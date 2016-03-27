package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingDAO extends GenericDAO<Booking, Integer> {

    void deleteHistoryBookings();

    List<Booking> getBookingByCriteria(int hotelId, LocalDate fromDate, LocalDate toDate, int roomNumber);
}
