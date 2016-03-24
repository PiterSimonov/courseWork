package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingDAO extends GenericDAO<Booking, Integer> {
    List<Booking> getActualBookingsByHotel(int hotelId);

    List<Booking> getHistoryBookingsByHotel(int hotelId);

    List<Booking> getBookingsByRoom(int roomId);

    List<Booking> getBookingsByHotel(int hotelId);

    List<Booking> getBookingByCriteria(int hotelId, LocalDate fromDate, LocalDate toDate, int roomNumber);
}
