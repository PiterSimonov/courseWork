package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.interfaces.BookingDAO;
import simonov.hotel.dao.interfaces.RoomDAO;
import simonov.hotel.entity.Booking;
import simonov.hotel.entity.Room;
import simonov.hotel.services.interfaces.BookingService;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {
    @Autowired
    BookingDAO bookingDAO;
    @Autowired
    RoomDAO roomDAO;

    @Override
    public List<Booking> getBookingByCriteria(int hotelId, LocalDate fromDate, LocalDate toDate, int roomNumber) {
        return bookingDAO.getBookingByCriteria(hotelId, fromDate, toDate, roomNumber);
    }

    @Override
    public List<Room> saveAll(List<Booking> bookings) {
        bookings.sort((o1, o2) -> o1.getRoom().getId() - o2.getRoom().getId());  // for prevent deadlock
        List<Room> result = new ArrayList<>();
        for (Booking b : bookings) {
            roomDAO.setLock(b.getRoom().getId());
            if (!roomDAO.isFree(b.getStartDate(), b.getEndDate(), b.getRoom().getId())) {
                result.add(b.getRoom());
            }
        }
        if (result.isEmpty()) {
            for (Booking booking : bookings) {
                bookingDAO.save(booking);
            }
        }
        bookings.stream().forEach(booking -> roomDAO.unlock(booking.getRoom().getId()));
        return result;
    }

    @Override
    public void deleteHistoryBookings() {
        bookingDAO.deleteHistoryBookings();
    }

    @PostConstruct
    private void deleteOldBooking() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });
        service.scheduleWithFixedDelay((Runnable) this::deleteHistoryBookings, 0, 1, TimeUnit.DAYS);
    }
}
