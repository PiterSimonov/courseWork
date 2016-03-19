package simonov.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import simonov.hotel.entity.*;
import simonov.hotel.services.interfaces.CityService;
import simonov.hotel.services.interfaces.CountryService;
import simonov.hotel.services.interfaces.HotelService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@EnableWebMvc
@RequestMapping("search")
public class SearchController {

    @Autowired
    HotelService hotelService;
    //    @Autowired
//    RoomService roomService;
//    @Autowired
//    BookingService bookingService;
//    @Autowired
//    ServletContext servletContext;
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
}
