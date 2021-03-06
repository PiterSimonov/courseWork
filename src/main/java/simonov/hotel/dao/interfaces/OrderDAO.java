package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.Order;

import java.util.List;

public interface OrderDAO extends GenericDAO<Order, Integer> {

    List<Order> getOrdersByUser(int userId, int firstOrder, int limit);

    List<Order> getNotConfirmedOrders();

    long getNotConfirmedOrdersCountByUser(int userId);
}
