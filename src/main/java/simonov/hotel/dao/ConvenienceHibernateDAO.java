package simonov.hotel.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.interfaces.ConvenienceDAO;
import simonov.hotel.entity.Convenience;

import java.util.List;

@SuppressWarnings("unchecked")
@Repository
public class ConvenienceHibernateDAO extends AbstractDAO<Convenience, Integer> implements ConvenienceDAO {

    public ConvenienceHibernateDAO() {
        super(Convenience.class);
    }

    @Override
    public List<Convenience> getConveniencesByHotel(Integer hotelId) {
        Criteria criteria = getCurrentSession().createCriteria(Convenience.class);
        criteria.createCriteria("hotels","hotel");
        criteria.add(Restrictions.eq("hotel.id", hotelId));
        return criteria.list();
    }
}
