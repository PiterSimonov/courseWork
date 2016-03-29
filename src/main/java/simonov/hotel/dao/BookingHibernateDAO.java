package simonov.hotel.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public void deleteHistoryBookings() {
            Query query = getCurrentSession().createQuery(" delete from Booking where endDate<:today");
            query.setParameter("today", LocalDate.now());
        query.executeUpdate();
    }

    @Override
    public List<Booking> getBookingByCriteria(int hotelId, LocalDate fromDate, LocalDate toDate, int roomNumber) {
        Criteria criteria = getCurrentSession().createCriteria(Booking.class);
        criteria.add(Restrictions.le("startDate",toDate));
        criteria.add(Restrictions.ge("endDate",fromDate));
        criteria.createAlias("room","r");
        criteria.createAlias("r.hotel","hotel");
        criteria.add(Restrictions.eq("hotel.id",hotelId));
        if (roomNumber>0){
            criteria.add(Restrictions.eq("r.number",roomNumber));
        }
        return criteria.list();
    }
}
