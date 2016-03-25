package simonov.hotel.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.interfaces.RoomDAO;
import simonov.hotel.entity.Booking;
import simonov.hotel.entity.Request;
import simonov.hotel.entity.Room;
import simonov.hotel.entity.RoomSort;

import java.time.LocalDate;
import java.util.List;

import static simonov.hotel.entity.RoomSort.*;

@Repository
@SuppressWarnings("unchecked")
public class RoomHibernateDAO extends AbstractDAO<Room, Integer> implements RoomDAO {

    public RoomHibernateDAO() {
        super(Room.class);
    }

    @Override
    public boolean isFree(LocalDate startDate, LocalDate endDate, int roomId) {
        Query query = getCurrentSession().createQuery("from Booking where room.id = :roomId" +
                " and (endDate>=:startDate and startDate<=:endDate)");
        query.setInteger("roomId", roomId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        List<Booking> bookings = query.list();
        return bookings.size() == 0;
    }

    @Override
    public boolean setLock(int id) {
        String stringQuery = "update Room r set r.locked = true where r.id = :id";
        Query query = getCurrentSession().createQuery(stringQuery);
        int update = query.setInteger("id", id).executeUpdate();
        return update != 0;
    }

    @Override
    public void unlock(int id) {
        String query = "update Room r set r.locked = false where r.id = :id";
        getCurrentSession().createQuery(query).setInteger("id", id).executeUpdate();
    }

    @Override
    public List<Room> getRoomsByHotel(int hotelId) {
        Criteria criteria = getCurrentSession().createCriteria(Room.class);
        criteria.add(Restrictions.eq("hotel.id", hotelId));
        return criteria.list();
    }

    @Override
    public List<Room> getRoomsByType(int hotelId, String type) {
        Criteria criteria = getCurrentSession().createCriteria(Room.class);
        criteria.add(Restrictions.eq("hotel.id", hotelId))
                .add(Restrictions.eq("type", type));
        return criteria.list();
    }

    @Override
    public List<Room> getFreeRoomsByRequest(Request request) {
        /*   Select * from room as r where hotel_id=?
              and (seats =? or seats=? or ...)
              and r.id not IN
              (SELECT room_id FROM booking WHERE startDate<=? AND endDate>=?) order by seats limit 0 5 */

        Criteria criteria = getCurrentSession().createCriteria(Room.class, "room");
        criteria.createAlias("room.hotel", "hotel");
        criteria.add(Restrictions.eq("hotel.id", request.getRoomHotelId()));
        Disjunction disjunction = Restrictions.disjunction();
        request.getSeats().keySet().stream()
                .forEach(integer -> disjunction.add(Restrictions.eq("room.seats", integer)));
        criteria.add(disjunction);
        DetachedCriteria bookingCriteria = DetachedCriteria.forClass(Booking.class, "booking");
        bookingCriteria.add(Restrictions.and(
                Restrictions.le("booking.startDate", request.getEndDate()),
                Restrictions.ge("booking.endDate", request.getStartDate())));
        bookingCriteria.createAlias("booking.room", "r");
        bookingCriteria.setProjection(Projections.property("r.id"));
        criteria.add(Subqueries.propertyNotIn("room.id", bookingCriteria))
                .setFirstResult(request.getRoomsFirstResult())
                .setMaxResults(request.getRoomsLimit());
        switch (request.getRoomSort()) {
            case SeatsAsc:
                criteria.addOrder(Order.asc("seats"));
                break;
            case SeatsDesc:
                criteria.addOrder(Order.desc("seats"));
                break;
            case PriceAsc:
                criteria.addOrder(Order.asc("price"));
                break;
            case PriceDesc:
                criteria.addOrder(Order.desc("price"));
                break;
        }

        return criteria.list();
    }
}
