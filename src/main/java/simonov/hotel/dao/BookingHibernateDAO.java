package simonov.hotel.dao;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.interfaces.BookingDAO;
import simonov.hotel.entity.Booking;

import java.time.LocalDate;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class BookingHibernateDAO extends AbstractDAO<Booking, Integer> implements BookingDAO {

    public BookingHibernateDAO() {
        super(Booking.class);
    }

    @Override
    public List<Booking> getBookingsByRoom(int roomId) {
        return getCurrentSession().createCriteria(Booking.class)
                .add(Restrictions.eq("room.id", roomId)).list();
    }

    @Override
    public List<Booking> getBookingsByHotel(int hotelId) {
        Query query = getCurrentSession().createQuery("from Booking where room in " +
                "(from Room as r where r.hotel.id = :hotelId)");
        query.setParameter("hotelId", hotelId);
        return query.list();
    }

    @Override
    public List<Booking> getActualBookingsByHotel(int hotelId) {
        Query query = getCurrentSession().createQuery("from Booking where room in " +
                "(from Room as r where r.hotel.id = :hotelId) and startDate>=:today");
        query.setParameter("hotelId", hotelId);
        query.setParameter("today", LocalDate.now());
        return query.list();
    }

    @Override
    public List<Booking> getHistoryBookingsByHotel(int hotelId) {
        Query query = getCurrentSession().createQuery("from Booking where room in " +
                "(from Room as r where r.hotel.id = :hotelId) and startDate<:today");
        query.setParameter("hotelId", hotelId);
        query.setParameter("today", LocalDate.now());
        return query.list();
    }
}
