package simonov.hotel.controllers;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import simonov.hotel.entity.*;
import simonov.hotel.services.interfaces.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.*;

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
    @Autowired
    BookingService bookingService;
    @Autowired
    CityService cityService;
    @Autowired
    CountryService countryService;


    @RequestMapping(value = "test", method = RequestMethod.POST, headers = "Accept=application/json")
    public
    @ResponseBody
    void search(@RequestBody Request request, Model model) {
        model.addAttribute("request", request);
    }

    @RequestMapping("hotels")
    public String mainSearch(@ModelAttribute Request request, Model model) {
        model.addAttribute("hotels", hotelService.getHotelsWithFreeRoom(request));
        return "search/hotels";
    }

    @RequestMapping(value = "nextHotels/{firstResult}")
    public String searchNext(@ModelAttribute Request request, @PathVariable int firstResult, Model model) {
        request.setFirstResult(firstResult);
        model.addAttribute("request", request);
        model.addAttribute("hotels", hotelService.getHotelsWithFreeRoom(request));
        return "search/nextHotels";
    }

    @RequestMapping(value = "hotel/{hotelId}/rooms", method = RequestMethod.GET)
    public String roomsSearch(@PathVariable int hotelId,
                              @ModelAttribute Request request,
                              Model model) {
        request.setHotelId(hotelId);
        request.setStartDate(LocalDate.parse("2016-03-05"));
        request.setEndDate(LocalDate.parse("2016-05-19"));
        request.setFirstResult(0);
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
        orderService.createOrder(bookings, user); //TODO if room not booking - > return back without this room
        return "redirect:/profile";
    }

    @RequestMapping(value = "check-date", method = RequestMethod.GET)
    public
    @ResponseBody
    boolean checkUser(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
                      @RequestParam int roomId, @ModelAttribute User user) {
        if (user.getRole() != Role.NotAuthorized && roomService.isFree(fromDate, toDate, roomId)) {
                Booking booking = new Booking();
                booking.setStartDate(fromDate);
                booking.setEndDate(toDate);
                booking.setRoom(roomService.getRoomById(roomId));
                bookingService.saveAll(Collections.singletonList(booking));//TODO rebuild this
                return true;
        } else {
            return false;
        }
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

    @RequestMapping(value = "/hotel/{name}/{cityId}/{countryId}")
    public
    @ResponseBody
    String searchHotels(@PathVariable String name, @PathVariable int cityId, @PathVariable int countryId) {
        List<Hotel> list = hotelService.getHotelsByName(name, cityId, countryId);
        JSONArray array = new JSONArray();
        list.stream().forEach(hotel -> array.add(hotel.toJSON()));
        return array.toString();
    }

    @RequestMapping(value = "/country/{name}")
    public
    @ResponseBody
    String searchCities(@PathVariable String name) {
        List<Country> list = countryService.getCountriesByNameCriteria(name);
        JSONArray array = new JSONArray();
        list.stream().forEach(country -> array.add(country.toJSON()));
        return array.toString();
    }

    @RequestMapping(value = "/city/{name}/{countryId}")
    public
    @ResponseBody
    String searchCountries(@PathVariable String name, @PathVariable int countryId) {
        List<City> list = cityService.getCitiesByCriteria(name, countryId);
        JSONArray array = new JSONArray();
        list.stream().forEach(city -> array.add(city.toJSON()));
        return array.toString();
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
