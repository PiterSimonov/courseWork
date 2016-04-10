package simonov.hotel.controllers;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import simonov.hotel.entity.*;
import simonov.hotel.services.interfaces.*;

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
    CityService cityService;
    @Autowired
    CountryService countryService;
    @Autowired
    CommentService commentService;
    @Autowired
    ConvenienceService convenienceService;

    @RequestMapping(value = "hotels", method = RequestMethod.POST, headers = "Accept=application/json")
    public String mainSearch(@RequestBody Request request, Model model) {
        model.addAttribute("request", request);
        model.addAttribute("hotels", hotelService.getHotelsWithFreeRoom(request));
        return "search/searchHotelTable";
    }

    @RequestMapping(value = "hotels", method = RequestMethod.GET)
    public String getSearchOnBackKey(@ModelAttribute Request request, Model model) {
        request.setHotelId(0);
        model.addAttribute("hotels", hotelService.getHotelsWithFreeRoom(request));
        return "search/hotels";
    }

    @RequestMapping(value = "nextHotels/{firstResult}")
    public String searchNextHotels(@ModelAttribute Request request, @PathVariable int firstResult, Model model) {
        request.setFirstResult(firstResult);
        model.addAttribute("hotels", hotelService.getHotelsWithFreeRoom(request));
        request.setFirstResult(0);
        return "search/searchHotelTable";
    }

    @RequestMapping(value = "hotel/{roomHotelId}/rooms", method = RequestMethod.GET)
    public String roomsSearch(@PathVariable int roomHotelId,
                              @ModelAttribute Request request,
                              Model model) {
        request.setRoomHotelId(roomHotelId);
        List<Room> rooms = roomService.getFreeRoomsByRequest(request);
        model.addAttribute("hotel", hotelService.getHotelById(roomHotelId));
        model.addAttribute("services",convenienceService.getConveniencesByHotel(roomHotelId));
        model.addAttribute("choice", new Choice());
        model.addAttribute("rooms", rooms);
        return "search/rooms";
    }

    @RequestMapping(value = "hotel/rooms/sort/{sort}")
    public
    @ResponseBody
    List<Room> roomsSort(@PathVariable int sort, @ModelAttribute Request request) {
        request.setRoomSort(RoomSort.values()[sort]);
        request.setRoomsFirstResult(0);
        return roomService.getFreeRoomsByRequest(request);
    }

    @RequestMapping(value = "roomsNext", method = RequestMethod.POST)
    public
    @ResponseBody
    List<Room> getNextFreeRoom(@ModelAttribute Request request,
                               @RequestParam("lastRoom") int lastRoom) {
        request.setRoomsFirstResult(lastRoom);
        List<Room> rooms = roomService.getFreeRoomsByRequest(request);
        request.setRoomsFirstResult(0);
        return rooms;
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
        cityService.getCitiesByCountryId(countryId,0,-1).stream().forEach(city -> map.put(city.getId(), city.getName()));
        return map;
    }

    @RequestMapping(value = "/hotel/{name}/{cityId}/{countryId}")
    public
    @ResponseBody
    String searchHotels(@PathVariable String name, @PathVariable int cityId, @PathVariable int countryId) {
        List<Hotel> list = hotelService.getHotelsByName(name, cityId, countryId, 0, 5);
        JSONArray array = new JSONArray();
        list.stream().forEach(hotel -> array.add(hotel.toJSON()));
        return array.toString();
    }

    @RequestMapping(value = "/country/{name}")
    public
    @ResponseBody
    String searchCities(@PathVariable String name) {
        List<Country> list = countryService.getCountriesByNameCriteria(name, 0, 5);
        JSONArray array = new JSONArray();
        list.stream().forEach(country -> array.add(country.toJSON()));
        return array.toString();
    }

    @RequestMapping(value = "/city/{name}/{countryId}")
    public
    @ResponseBody
    String searchCountries(@PathVariable String name, @PathVariable int countryId) {
        List<City> list = cityService.getCitiesByCriteria(name, countryId, 0, 5);
        JSONArray array = new JSONArray();
        list.stream().forEach(city -> array.add(city.toJSON()));
        return array.toString();
    }

    @RequestMapping(value = "/comments/{hotelId}/{firstResult}")
    public
    @ResponseBody
    List<Comment> getCommentsByHotel(@PathVariable int hotelId,
                               @PathVariable int firstResult) {
        return commentService.getCommentsByHotel(hotelId, firstResult, 7);
    }

    @ModelAttribute("user")
    public User createUser() {
        return new User();
    }

    @ModelAttribute("request")
    public Request createRequest() {
        return new Request();
    }
}
