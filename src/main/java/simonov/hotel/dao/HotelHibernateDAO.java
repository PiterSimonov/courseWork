package simonov.hotel.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.interfaces.HotelDAO;
import simonov.hotel.entity.Hotel;
import simonov.hotel.entity.SearchRequest;

import java.util.List;
import java.util.Map;

@Repository
@SuppressWarnings("unchecked")
public class HotelHibernateDAO extends AbstractDAO<Hotel, Integer> implements HotelDAO {

    public HotelHibernateDAO() {
        super(Hotel.class);
    }

    @Override
    public List<Hotel> getHotelsByUser(Integer userId, int firstHotel, int limit) {
        Criteria hotelCriteria = getCurrentSession().createCriteria(Hotel.class);
        hotelCriteria.add(Restrictions.eq("user.id", userId));
        hotelCriteria.setFirstResult(firstHotel);
        hotelCriteria.setMaxResults(limit);
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
    public List<Hotel> getHotelsWithFreeRoom(SearchRequest searchRequest) {
        Query query = getCurrentSession().createQuery(getPreparedQuery(searchRequest));
        query.setParameter("startDate", searchRequest.getStartDate());
        query.setParameter("endDate", searchRequest.getEndDate());
        if (searchRequest.getHotelId() != 0) {
            query.setInteger("hotelId", searchRequest.getHotelId());
        } else if (searchRequest.getCityId() != 0) {
            query.setInteger("cityId", searchRequest.getCityId());
        } else if (searchRequest.getCountryId() != 0) {
            query.setInteger("countryId", searchRequest.getCountryId());
        }
        if (searchRequest.getStars() != 0) {
            query.setInteger("stars", searchRequest.getStars());
        }
        if (searchRequest.getFirstResult() != 0) {
            query.setFirstResult(searchRequest.getFirstResult());
        }
        if (searchRequest.getLimit() != 0) {
            query.setMaxResults(searchRequest.getLimit());
        }
        if (searchRequest.getSeats() != null) {
            int index = 1;
            for (Map.Entry entry : searchRequest.getSeats().entrySet()) {
                query.setParameter("seats" + index, entry.getKey());
                query.setParameter("value" + index, ((Integer) entry.getValue()).longValue());
                index++;
            }
        }
        return query.list();
    }

    private String getPreparedQuery(SearchRequest searchRequest) {
        StringBuilder query = new StringBuilder("from Hotel as h where ");
        if (searchRequest.getHotelId() != 0) {
            query.append("h.id = :hotelId ");
        } else if (searchRequest.getCityId() != 0) {
            query.append("h.city.id = :cityId ");
        } else if (searchRequest.getCountryId() != 0) {
            query.append("h.city in (select c.id from City as c where c.country.id = :countryId) ");
        }
        if (searchRequest.getSeats() != null) {
            int index = 1;
            for (Map.Entry entry : searchRequest.getSeats().entrySet()) {
                query.append(" and (select count(r.id) from Room as r where h.id = r.hotel.id and r.seats = :seats" + index + " and not exists " +
                        "(select distinct b.room.id from Booking as b where r.id = b.room.id and (endDate>=:startDate and startDate<=:endDate)))>=:value"
                        + index);
                index++;
            }
        }
        if (searchRequest.getStars() > 0) {
            query.append(" and h.stars = :stars");
        }
        return query.toString();
    }
}

