package simonov.hotel.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.interfaces.OrderDAO;
import simonov.hotel.entity.Order;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class OrderHibernateDAO extends AbstractDAO<Order, Integer> implements OrderDAO {
    public OrderHibernateDAO() {
        super(Order.class);
    }

    @Override
    public List<Order> getOrdersByUser(int userId) {
        Query query = getCurrentSession().createQuery("from Order where user.id=:userId");
        query.setParameter("userId", userId);
        List<Order> orders = query.list();
        orders.stream().forEach(order -> order.getBookings().size());
        return orders;
    }
}
