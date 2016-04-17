package simonov.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import simonov.hotel.entity.Hotel;
import simonov.hotel.entity.Order;
import simonov.hotel.entity.Role;
import simonov.hotel.entity.User;
import simonov.hotel.services.interfaces.*;

import java.util.List;

@Controller
@EnableWebMvc
@SessionAttributes({"user", "hotel"})
public class AuthenticationController {
    @Autowired
    UserService userService;
    @Autowired
    HotelService hotelService;
    @Autowired
    CountryService countryService;
    @Autowired
    OrderService orderService;
    @Autowired
    ConvenienceService convenienceService;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") User user, Model model) {
        if (userService.save(user)) {
            model.addAttribute("user", user);
            return "forms/loginForm";
        } else {
            model.addAttribute("user", new User());
            return "";
        }
    }

    @RequestMapping(value = "/get-user", method = RequestMethod.GET)
    public
    @ResponseBody
    Role getUserRole(@ModelAttribute("user") User user) {
        return user.getRole();
    }

    @RequestMapping(value = "/check-user", method = RequestMethod.POST)
    public
    @ResponseBody
    String checkUser(@ModelAttribute("user") User user, Model model) {
        User loggedUser = userService.getLoggedUser(user.getLogin(), user.getPassword());
        if (loggedUser != null) {
            model.addAttribute("user", loggedUser);
            return "logged";
        } else {
            return "Wrong login or password!";
        }
    }

    @RequestMapping(value = "/check-login")
    public
    @ResponseBody
    boolean checkLoginIsFree(@RequestParam String login) {
        return userService.isLoginFree(login);
    }

    @RequestMapping(value = "/check-email")
    public
    @ResponseBody
    boolean checkEmailIsFree(@RequestParam String email) {
        return userService.isEmailFree(email);
    }

    @RequestMapping("/profile")
    public String userProfile(@ModelAttribute("user") User user, Model model) {
        if (user.getRole() == Role.HotelOwner) {
            List<Hotel> hotels = hotelService.getHotelsByUser(user.getId(),0,5);
            model.addAttribute("hotels", hotels);
            model.addAttribute("countries",countryService.getAllCountries());
            model.addAttribute("services", convenienceService.getAll());
            return "hotelsOwnerProfile";
        } else if (user.getRole() == Role.CLIENT) {
            List<Order> orders = orderService.getOrdersByUser(user.getId(),0,-1);
            model.addAttribute("orders", orders);
            return "clientProfile";
        } else return "redirect:/";
    }

    @RequestMapping(value = "nextHotels", method = RequestMethod.GET)
    public @ResponseBody List<Hotel> nextHotels(@ModelAttribute User user, @RequestParam int lastHotel){
        return hotelService.getHotelsByUser(user.getId(),lastHotel,5);

    }

    @RequestMapping(value = "/profile/{userId}/edit", method = RequestMethod.GET)
    public String editUserProfile(@PathVariable int userId,
                                  @ModelAttribute("user") User user) {
        if (userId == user.getId()) {
            return "editProfile";
        }
        return "error";
    }

    @RequestMapping(value = "/profile/{userId}/edit", method = RequestMethod.POST)
    public String saveProfile(@PathVariable int userId,
                              @ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/profile";
    }

    @RequestMapping(value = "/update-user", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam String lastName,
                             @RequestParam String firstName,
                             @RequestParam String phone) {
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setPhone(phone);
        userService.update(user);
        return "userProfile";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(SessionStatus status) {
        status.setComplete();
        return "redirect:/";
    }

    @ModelAttribute("user")
    public User createUser() {
        return new User();
    }
}
