package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.interfaces.BookingDAO;
import simonov.hotel.dao.interfaces.RoomDAO;
import simonov.hotel.entity.Booking;
import simonov.hotel.entity.Room;
import simonov.hotel.services.interfaces.BookingService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingDAO bookingDAO;
    @Autowired
    RoomDAO roomDAO;

    @Override
    public List<Booking> getBookingsByRoom(int roomId) {
        return bookingDAO.getBookingsByRoom(roomId);
    }

    @Override
    public List<Booking> getBookingsByHotel(int hotelId) {
        return bookingDAO.getBookingsByHotel(hotelId);
    }

    @Override
    public List<Booking> getActualBookingsByHotel(int hotelId) {
        return bookingDAO.getActualBookingsByHotel(hotelId);
    }

    @Override
    public List<Booking> getHistoryBookingsByHotel(int hotelId) {
        return bookingDAO.getHistoryBookingsByHotel(hotelId);
    }

    @Override
    public void delete(Booking booking, String message) {
        bookingDAO.delete(booking);
    }

    @Override
    public List<Booking> getBookingByCriteria(int hotelId, LocalDate fromDate, LocalDate toDate, int roomNumber) {
        return bookingDAO.getBookingByCriteria(hotelId,fromDate,toDate,roomNumber);
    }

    @Override
    public void update(Booking booking) {
        bookingDAO.update(booking);
    }

    @Override
    public List<Booking> getBookings() {
        return bookingDAO.getAll();
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
                roomDAO.unlock(booking.getRoom().getId());
            }
        } else {
            bookings.stream().forEach(booking -> roomDAO.unlock(booking.getRoom().getId()));
        }
        return result;
    }
}
