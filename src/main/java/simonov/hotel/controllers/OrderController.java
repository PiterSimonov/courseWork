package simonov.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import simonov.hotel.entity.*;
import simonov.hotel.services.interfaces.CommentService;
import simonov.hotel.services.interfaces.HotelService;
import simonov.hotel.services.interfaces.OrderService;
import simonov.hotel.services.interfaces.RoomService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@EnableWebMvc
@SessionAttributes({"user", "searchRequest"})
@RequestMapping("order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    HotelService hotelService;
    @Autowired
    CommentService commentService;
    @Autowired
    RoomService roomService;

    @RequestMapping(value = "{orderId}/comment/{hotelId}", method = RequestMethod.GET)
    public String openCommentPage(@PathVariable int orderId, @PathVariable int hotelId,
                                  @ModelAttribute("user") User user, Model model) {
        Order order = orderService.getOrderById(orderId);
        if (order != null && user.getId() == order.getUser().getId() &&
                !order.isCommented() && order.getStatus() == Status.Confirmed) {
            return "comment";
        } else {
            model.addAttribute("message", "Wrong URL for leave comment");
            return "error";
        }
    }

    @RequestMapping(value = "{orderId}/comment/{hotelId}", method = RequestMethod.POST)
    public String saveComment(@PathVariable int orderId,
                              @PathVariable int hotelId,
                              @ModelAttribute("user") User user,
                              @RequestParam("comment") String commentText,
                              @RequestParam int rating) {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setComment(commentText);
        comment.setRating(rating);
        Hotel hotel = hotelService.getHotelById(hotelId);
        comment.setHotel(hotel);
        commentService.save(comment);
        orderService.setCommented(orderId);
        hotel.setRating(commentService.getAvgRatingByHotel(hotelId));
        hotelService.update(hotel);
        return "redirect:/profile";
    }

    @RequestMapping(value = "{orderId}/payment", method = RequestMethod.GET)
    public String getPaymentPage(@PathVariable int orderId, @ModelAttribute("user") User user, Model model) {
        Order order = orderService.getOrderById(orderId);
        if (order != null && user.getId() == order.getUser().getId()) {
            return "payment";
        } else {
            model.addAttribute("message", "Wrong URL for Payment");
            return "error";
        }
    }

    @RequestMapping(value = "{orderId}/payment", method = RequestMethod.POST)
    public String payment(@PathVariable int orderId, @ModelAttribute User user, Model model) {
        Order order = orderService.getOrderById(orderId);
        if (order != null && user.getId() == order.getUser().getId()) {
            // some extra work for payment
            orderService.updateStatus(order);
            return "redirect:/profile";
        } else {
            model.addAttribute("message", "Wrong URL for Payment");
            return "error";
        }
    }

    @RequestMapping(value = "{orderId}/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    boolean deleteOrder(@PathVariable int orderId, @ModelAttribute("user") User user) {
        Order order = orderService.getOrderById(orderId);
        if (order != null && user.getId() == order.getUser().getId()) {
            orderService.delete(order);
            return true;
        } else {
            return false;
        }
    }

    @RequestMapping(value = "check-date", method = RequestMethod.GET)
    public
    @ResponseBody
    boolean checkDateForOrder(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
                              @RequestParam int roomId, @ModelAttribute User user) {
        long userOrderCount = orderService.getNotConfirmedOrdersCountByUser(user.getId());
        if (user.getRole() != Role.NotAuthorized && userOrderCount < 8
                && roomService.isFree(fromDate, toDate, roomId)) {
            Booking booking = new Booking();
            booking.setStartDate(fromDate);
            booking.setEndDate(toDate);
            booking.setRoom(roomService.getRoomById(roomId));
            List<Room> notFreeRooms = orderService.createOrder(Collections.singletonList(booking), user);
            return notFreeRooms.size() == 0;
        } else {
            return false;
        }
    }

    @RequestMapping(value = "hotel/{hotelId}/rooms", method = RequestMethod.POST)
    public String createOrder(@PathVariable int hotelId, @ModelAttribute SearchRequest searchRequest,
                              @ModelAttribute Choice choice,
                              @ModelAttribute("user") User user, Model model) {
        if (orderService.getNotConfirmedOrdersCountByUser(user.getId()) >= 8) {
            model.addAttribute("message", "Sorry, but you have 8 unconfirmed orders. This is enough");
            return "error";
        }
        if (user.getId()==hotelService.getHotelById(hotelId).getUser().getId()){
            model.addAttribute("message", "Sorry, but for the owners of hotels, this operation is not allowed");
            return "error";
        }
        List<Integer> ids = choice.getRoomsIds();
        List<Booking> bookings = new ArrayList<>();
        for (int i : ids) {
            Booking booking = new Booking();
            booking.setRoom(roomService.getRoomById(i));
            booking.setStartDate(searchRequest.getStartDate());
            booking.setEndDate(searchRequest.getEndDate());
            bookings.add(booking);
        }
        List<Room> rooms = orderService.createOrder(bookings, user);
        if (rooms.size() == 0) {
            return "redirect:/profile";
        } else {
            String roomNumbers = rooms.stream().map(room -> String.valueOf(room.getNumber())).collect(Collectors.joining(", "));
            model.addAttribute("message", "Sorry, but room №: " + roomNumbers + " already booked");
            List<Room> roomList = roomService.getFreeRoomsByRequest(searchRequest);
            model.addAttribute("choice", new Choice());
            model.addAttribute("rooms", roomList);
            return "search/rooms";
        }
    }

    @ModelAttribute("user")
    public User createUser() {
        return new User();
    }

    @ModelAttribute("searchRequest")
    public SearchRequest createRequest() {
        return new SearchRequest();
    }
}