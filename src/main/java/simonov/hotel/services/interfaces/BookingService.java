package simonov.hotel.services.interfaces;

import org.springframework.stereotype.Service;
import simonov.hotel.entity.Booking;
import simonov.hotel.entity.Room;

import java.time.LocalDate;
import java.util.List;

@Service
public interface BookingService {
    List<Room> saveAll(List<Booking> bookings);

    void deleteHistoryBookings();

    List<Booking> getBookingByCriteria(int hotelId, LocalDate fromDate, LocalDate toDate, int roomNumber);
}
