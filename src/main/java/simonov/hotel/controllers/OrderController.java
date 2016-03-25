package simonov.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import simonov.hotel.entity.*;
import simonov.hotel.services.interfaces.CommentService;
import simonov.hotel.services.interfaces.HotelService;
import simonov.hotel.services.interfaces.OrderService;

@Controller
@EnableWebMvc
@SessionAttributes("user")
@RequestMapping("order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    HotelService hotelService;
    @Autowired
    CommentService commentService;

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
        comment.setHotel(hotelService.getHotelById(hotelId));
        commentService.save(comment);
        orderService.setCommented(orderId);
        return "redirect:/profile";
    }

    @RequestMapping(value = "{orderId}/payment", method = RequestMethod.GET)
    public String getPaymentPage(@PathVariable int orderId, @ModelAttribute("user") User user, Model model) {
        Order order = orderService.getOrderById(orderId);
        if (user.getId() == order.getUser().getId()) {
            return "payment";
        } else {
            model.addAttribute("message", "Wrong URL for Payment");
            return "error";
        }
    }
    @RequestMapping(value = "{orderId}/delete", method = RequestMethod.GET)
    public String deleteOrder(@PathVariable int orderId, @ModelAttribute("user") User user, Model model) {
        Order order = orderService.getOrderById(orderId);
        if (user.getId() == order.getUser().getId()) {
            orderService.delete(order);
            return "redirect:/profile";
        } else {
            model.addAttribute("message", "Wrong URL for delete order");
            return "error";
        }
    }


    @RequestMapping(value = "{orderId}/payment", method = RequestMethod.POST)
    public String payment(@PathVariable int orderId) {
        Order order = orderService.getOrderById(orderId);
        // some extra work for payment
        orderService.updateStatus(order);
        return "redirect:/profile";
    }

    @ModelAttribute("user")
    public User createUser() {
        User user = new User();
        user.setRole(Role.NotAuthorized);
        return user;
    }
}
