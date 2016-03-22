package simonov.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import simonov.hotel.entity.*;
import simonov.hotel.services.interfaces.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@EnableWebMvc
@RequestMapping("search")
@SessionAttributes({"user", "request"})
public class SearchController {

    @Autowired
    HotelService hotelService;
    @Autowired
    RoomService roomService;
    @Autowired
    OrderService orderService;
    //    @Autowired
//    BookingService bookingService;
    @Autowired
    CityService cityService;
    @Autowired
    CountryService countryService;

    @RequestMapping
    public String search(@RequestParam(required = false) Integer country,
                         @RequestParam(required = false) Integer city,
                         @RequestParam(required = false) Integer hotel,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
                         @RequestParam(required = false) Integer numOfTravelers,
                         Model model) {
        Request request = new Request();
//        request.setCountryId(country);
//        request.setCityId(city);
//        request.setHotelId(hotel);
        request.setStartDate(fromDate);
        request.setEndDate(toDate);
        Map<Integer, Integer> seats = new HashMap<>();
        seats.put(2, 2);
        request.setSeats(seats);
        model.addAttribute("hotels", hotelService.getHotelsWithFreeRoom(request));
        return "main";
    }

    @RequestMapping("hotels")
    public String mainSearch(@ModelAttribute Request request) {
        hotelService.getHotelsWithFreeRoom(request);
        return "search/hotels";
    }

    @RequestMapping(value = "hotel/{hotelId}/rooms", method = RequestMethod.GET)
    public String roomsSearch(@PathVariable int hotelId,
                              @ModelAttribute Request request,
                              Model model) {
        request.setHotelId(hotelId);
        request.setStartDate(LocalDate.parse("2016-03-05"));
        request.setEndDate(LocalDate.parse("2016-05-19"));
        Map<Integer, Integer> seats = new HashMap<>();
        seats.put(2, 1);
        seats.put(1, 1);
        seats.put(3, 1);
        request.setSeats(seats);
        List<Room> rooms = roomService.getFreeRoomsByRequest(request);
        model.addAttribute("choice", new Choice());
        model.addAttribute("rooms", rooms);
        return "search/rooms";
    }

    @RequestMapping(value = "roomsNext", method = RequestMethod.POST)
    public
    @ResponseBody
    List<Room> getNextFreeRoom(@ModelAttribute Request request,
                               @RequestParam("lastRoom") int lastRoom) {
        System.out.println("Inside roomNext" + lastRoom);
        request.setFirstResult(lastRoom);
        List<Room> rooms = roomService.getFreeRoomsByRequest(request);
        rooms.stream().forEach(room -> System.out.println(room.getId()));
        return rooms;
    }

    @RequestMapping(value = "hotel/{hotelId}/rooms", method = RequestMethod.POST)
    public String createOrder(@PathVariable int hotelId, @ModelAttribute Request request,
                              @ModelAttribute Choice choice,
                              @ModelAttribute("user") User user
    ) {
        List<Integer> ids = choice.getRoomsIds();
        List<Booking> bookings = new ArrayList<>();
        for (int i : ids) {
            Booking booking = new Booking();
            booking.setRoom(roomService.getRoomById(i));
            booking.setStartDate(request.getStartDate());
            booking.setEndDate(request.getEndDate());
            bookings.add(booking);
        }
        orderService.createOrder(bookings, user);
        return "redirect:/profile";   //TODO need to be change
    }


    @RequestMapping(value = "/", params = "country")
    public
    @ResponseBody
    List<Country> getCountries(@RequestParam String country) {
        return countryService.getCountriesByNameCriteria(country);
    }

    @RequestMapping(value = "/country")
    public
    @ResponseBody
    Map<Integer, String> getCountries() {
        Map<Integer, String> map = new HashMap<>();
        countryService.getAllCountries().stream().forEach(country -> map.put(country.getId(), country.getName()));
        return map;
    }

    @RequestMapping(value = "/city")
    public
    @ResponseBody
    Map<Integer, String> getCities(@RequestParam("country_id") int countryId) {
        Map<Integer, String> map = new HashMap<>();
        cityService.getCitiesByCountryId(countryId).stream().forEach(city -> map.put(city.getId(), city.getName()));
        return map;
    }

    @RequestMapping(value = "/", params = {"city", "countryId"})
    public
    @ResponseBody
    List<City> getCities(@RequestParam String city, @RequestParam Integer countryId) {
        return cityService.getCitiesByCriteria(city, countryId);
    }

    @RequestMapping(value = "/", params = {"hotel", "cityId", "countryId"})
    public
    @ResponseBody
    List<Hotel> getHotels(@RequestParam String hotel, @RequestParam Integer city, @RequestParam Integer countryId) {
        return /*hotelService.getHotelsByCriteria(countryId,city,hotel)*/ null;
    }

    @ModelAttribute("user")
    public User createUser() {
        User user = new User();
        user.setRole(Role.NotAuthorized);
        return user;
    }

    @ModelAttribute("request")
    public Request createRequest() {
        Request request = new Request();
        request.setLimit(5);
        request.setFirstResult(0);
        return request;
    }

}
