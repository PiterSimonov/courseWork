package simonov.hotel.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.interfaces.OrderDAO;
import simonov.hotel.entity.Order;
import simonov.hotel.entity.Status;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class OrderHibernateDAO extends AbstractDAO<Order, Integer> implements OrderDAO {
    public OrderHibernateDAO() {
        super(Order.class);
    }

    @Override
    public List<Order> getOrdersByUser(int userId) {
        Criteria criteria = getCurrentSession().createCriteria(Order.class);
        criteria.add(Restrictions.eq("user.id", userId));
        criteria.addOrder(org.hibernate.criterion.Order.desc("creationTime"));
        return criteria.list();
    }

    @Override
    public List<Order> getNotConfirmedOrders() {
        Criteria criteria = getCurrentSession().createCriteria(Order.class);
        criteria.add(Restrictions.eq("status", Status.NotConfirmed));
        return criteria.list();
    }
}
