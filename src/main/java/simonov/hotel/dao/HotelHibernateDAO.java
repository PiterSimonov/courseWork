package simonov.hotel.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.interfaces.HotelDAO;
import simonov.hotel.entity.Hotel;
import simonov.hotel.entity.Request;

import java.util.List;
import java.util.Map;

@Repository
@SuppressWarnings("unchecked")
public class HotelHibernateDAO extends AbstractDAO<Hotel, Integer> implements HotelDAO {

    public HotelHibernateDAO() {
        super(Hotel.class);
    }

    @Override
    public List<Hotel> getHotelsByUser(Integer userId) {
        Criteria hotelCriteria = getCurrentSession().createCriteria(Hotel.class);
        hotelCriteria.add(Restrictions.eq("user.id", userId));
        return hotelCriteria.list();
    }

    @Override
    public List<Hotel> getHotelsByCriteria(Integer countryId, Integer cityId, String hotelName, int firstResult, int limit) {
        Criteria hotelCriteria = getCurrentSession().createCriteria(Hotel.class);

        if (cityId != null && cityId > 0) {
            hotelCriteria.createAlias("city", "city");
            hotelCriteria.add(Restrictions.eq("city.id", cityId));
        } else {
            if (countryId != null && countryId > 0) {
                hotelCriteria.createAlias("city", "city");
                hotelCriteria.createAlias("city.country", "country");
                hotelCriteria.add(Restrictions.eq("country.id", countryId));
            }
        }
        hotelCriteria.add(Restrictions.ilike("name", hotelName, MatchMode.ANYWHERE));
        hotelCriteria.setFirstResult(firstResult);
        hotelCriteria.setMaxResults(limit);

        return hotelCriteria.list();
    }

    @Override
    public List<Hotel> getHotels(int first,int limit) {
        Criteria hotelCriteria = getCurrentSession().createCriteria(Hotel.class);
        hotelCriteria.setFirstResult(first);
        hotelCriteria.setMaxResults(limit);
        hotelCriteria.addOrder(Order.desc("rating"))
                .addOrder(Order.desc("stars"));
        return hotelCriteria.list();
    }

    @Override
    public List<Hotel> getHotelsWithFreeRoom(Request request) {
        Query query = getCurrentSession().createQuery(getPreparedQuery(request));
        query.setParameter("startDate", request.getStartDate());
        query.setParameter("endDate", request.getEndDate());
        if (request.getHotelId() != 0) {
            query.setInteger("hotelId", request.getHotelId());
        } else if (request.getCityId() != 0) {
            query.setInteger("cityId", request.getCityId());
        } else if (request.getCountryId() != 0) {
            query.setInteger("countryId", request.getCountryId());
        }
        if (request.getStars() != 0) {
            query.setInteger("stars", request.getStars());
        }
        if (request.getFirstResult() != 0) {
            query.setFirstResult(request.getFirstResult());
        }
        if (request.getLimit() != 0) {
            query.setMaxResults(request.getLimit());
        }
        if (request.getSeats() != null) {
            int index = 1;
            for (Map.Entry entry : request.getSeats().entrySet()) {
                query.setParameter("seats" + index, entry.getKey());
                query.setParameter("value" + index, ((Integer) entry.getValue()).longValue());
                index++;
            }
        }
        return query.list();
    }

    private String getPreparedQuery(Request request) {
        StringBuilder query = new StringBuilder("from Hotel as h where ");
        if (request.getHotelId() != 0) {
            query.append("h.id = :hotelId ");
        } else if (request.getCityId() != 0) {
            query.append("h.city.id = :cityId ");
        } else if (request.getCountryId() != 0) {
            query.append("h.city in (select c.id from City as c where c.country.id = :countryId) ");
        }
        if (request.getSeats() != null) {
            int index = 1;
            for (Map.Entry entry : request.getSeats().entrySet()) {
                query.append(" and (select count(r.id) from Room as r where h.id = r.hotel.id and r.seats = :seats" + index + " and not exists " +
                        "(select distinct b.room.id from Booking as b where r.id = b.room.id and (endDate>=:startDate and startDate<=:endDate)))>=:value"
                        + index);
                index++;
            }
        }
        if (request.getStars() > 0) {
            query.append(" and h.stars = :stars");
        }
        return query.toString();
    }
}

