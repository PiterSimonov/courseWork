package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.interfaces.OrderDAO;
import simonov.hotel.entity.*;
import simonov.hotel.services.interfaces.BookingService;
import simonov.hotel.services.interfaces.OrderService;
import simonov.hotel.utilites.OrderControl;

import java.time.Period;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDAO orderDAO;
    @Autowired
    BookingService bookingService;
    @Autowired
    OrderControl orderControl;

    @Override
    public List<Room> createOrder(List<Booking> bookings, User user) {
        List<Room> rooms = null;
        if (!bookings.isEmpty()) {
            Order order = new Order();
            order.setCreationTime(new Date());
            order.setStatus(Status.NotConfirmed);
            order.setUser(user);
            order.setHotel(bookings.get(0).getRoom().getHotel());
            bookings.stream().forEach(booking -> booking.setOrder(order));
            rooms = bookingService.saveAll(bookings);
            if (rooms.isEmpty()) {
                order.setBookings(bookings);
                double price = 0;
                for (Booking b : bookings) {
                    int dayCount = Period.between(b.getStartDate(), b.getEndDate()).getDays();
                    price += dayCount * b.getRoom().getPrice();
                }
                order.setPrice(price);
                orderDAO.save(order);
                orderControl.addOrder(order);
            }
        }
        return rooms;
    }

    @Override
    public Integer save(Order order) {
        return orderDAO.save(order);
    }

    @Override
    public List<Order> getOrdersByUser(int userId) {
        return orderDAO.getOrdersByUser(userId);
    }

    @Override
    public List<Order> getNotConfirmedOrders() {
        return orderDAO.getNotConfirmedOrders();
    }

    @Override
    public long getNotConfirmedOrdersCountByUser(int userId) {
        return orderDAO.getNotConfirmedOrdersCountByUser(userId);
    }

    @Override
    public Order getOrderById(int id) {
        return orderDAO.get(id);
    }

    @Override
    public void setCommented(int orderId) {
        Order order = orderDAO.get(orderId);
        order.setCommented(true);
    }

    @Override
    public boolean updateStatus(Order order) {
        if (orderControl.removeOrder(order)) {
            order.setStatus(Status.Confirmed);
            orderDAO.update(order);
            return true;
        }
        return false;
    }

    @Override
    public void delete(Order order) {
        orderControl.removeOrder(order);
        orderDAO.delete(order);
    }
}
