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
        Order order = new Order();
        order.setCreationTime(new Date());
        order.setStatus(Status.NotConfirmed);
        order.setUser(user);
        bookings.stream().forEach(booking -> booking.setOrder(order));
        List<Room> rooms = bookingService.saveAll(bookings);
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
    public Order getOrderById(int id) {
        return orderDAO.get(id);
    }

    @Override
    public void setCommented(Order order) {
        order.setCommented(true);
        orderDAO.update(order);
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
        orderDAO.delete(order);
    }
}
