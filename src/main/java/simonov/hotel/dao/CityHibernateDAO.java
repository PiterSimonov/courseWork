package simonov.hotel.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.interfaces.CityDAO;
import simonov.hotel.entity.City;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class CityHibernateDAO extends AbstractDAO<City, Integer> implements CityDAO {

    public CityHibernateDAO() {
        super(City.class);
    }

    @Override
    public City getCityByName(String cityName) {
        Criteria criteria = getCurrentSession().createCriteria(City.class);
        criteria.add(Restrictions.eq("name", cityName));
        return (City) criteria.uniqueResult();
    }

    @Override
    public List<City> getCitiesByCriteria(String cityName) {
        Criteria criteria = getCurrentSession().createCriteria(City.class);
        criteria.add(Restrictions.ilike("name", cityName, MatchMode.ANYWHERE));
        return criteria.list();
    }

    @Override
    public List<City> getCitiesByCountryId(Integer countryId) {
        Criteria criteria = getCurrentSession().createCriteria(City.class);
        criteria.add(Restrictions.eq("country.id", countryId));
        return criteria.list();
    }

    @Override
    public List<City> getCitiesByCountryAndName(String cityName, Integer countryId) {
        Criteria criteria = getCurrentSession().createCriteria(City.class);
        criteria.add(Restrictions.eq("country.id", countryId))
                .add(Restrictions.ilike("name", cityName, MatchMode.ANYWHERE));
        return criteria.list();
    }


}
