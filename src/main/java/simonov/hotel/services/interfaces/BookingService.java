package simonov.hotel.services.interfaces;

import org.springframework.stereotype.Service;
import simonov.hotel.entity.Booking;
import simonov.hotel.entity.Room;

import java.time.LocalDate;
import java.util.List;

@Service
public interface BookingService {
    List<Room> saveAll(List<Booking> bookings);

    List<Booking> getActualBookingsByHotel(int hotelId);

    List<Booking> getHistoryBookingsByHotel(int hotelId);

    List<Booking> getBookingsByRoom(int roomId);

    List<Booking> getBookingsByHotel(int hotelId);

    void delete(Booking booking, String message);

    List<Booking> getBookings();

    void update(Booking booking);

    List<Booking> getBookingByCriteria(int hotelId, LocalDate fromDate, LocalDate toDate, int roomNumber);
}
