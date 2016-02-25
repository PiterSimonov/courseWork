package simonov.hotel.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.repository.AbstractDAO;
import simonov.hotel.dao.repository.IBookingDAO;
import simonov.hotel.entity.Booking;

import java.util.List;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class BookingDAO extends AbstractDAO<Booking, Integer> implements IBookingDAO {
    @Override
    public List<Booking> getBookingsByUser(int userId) {
        Query query = getCurrentSession().createQuery("from Booking where user.id = :userId");
        query.setInteger("userId", userId);
        return query.list();
    }

    @Override
    public List<Booking> getBookingByRoom(int roomId) {
        Query query = getCurrentSession().createQuery("from Booking where room.id = :roomId");
        query.setInteger("roomId", roomId);
        return query.list();
    }
}
