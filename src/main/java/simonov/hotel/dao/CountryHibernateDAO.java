package simonov.hotel.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.interfaces.CountryDAO;
import simonov.hotel.entity.Country;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class CountryHibernateDAO extends AbstractDAO<Country, Integer> implements CountryDAO {

    public CountryHibernateDAO() {
        super(Country.class);
    }

    @Override
    public List<Country> getCountriesByNameCriteria(String nameCriteria, int firstResult, int limit) {
        Criteria criteria = getCurrentSession().createCriteria(Country.class);
        criteria.add(Restrictions.ilike("name", nameCriteria, MatchMode.START));
        criteria.setFirstResult(firstResult);
        criteria.setMaxResults(limit);
        return criteria.list();
    }
}
