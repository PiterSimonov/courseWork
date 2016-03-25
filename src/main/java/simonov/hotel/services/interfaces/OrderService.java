package simonov.hotel.services.interfaces;

import simonov.hotel.entity.Booking;
import simonov.hotel.entity.Order;
import simonov.hotel.entity.Room;
import simonov.hotel.entity.User;

import java.util.List;

public interface OrderService {

    List<Order> getOrdersByUser(int userId);

    List<Order> getNotConfirmedOrders();

    Order getOrderById(int id);

    List<Room> createOrder(List<Booking> bookings, User user);

    Integer save(Order order);

    boolean updateStatus(Order order);

    void setCommented(int orderId);

    void delete(Order order);
}
