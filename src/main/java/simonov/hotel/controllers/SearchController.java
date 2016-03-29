package simonov.hotel.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import simonov.hotel.entity.*;
import simonov.hotel.services.interfaces.*;
import simonov.hotel.utilites.FileUpLoader;

import java.io.IOException;
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
        model.addAttribute("hotels", hotelService.getHotelsWithFreeRoom(request));
        request.setFirstResult(0);
        return "search/nextHotels";
    }

    @RequestMapping(value = "hotel/{roomHotelId}/rooms", method = RequestMethod.GET)
    public String roomsSearch(@PathVariable int roomHotelId,
                              @ModelAttribute Request request,
                              Model model) {
        request.setRoomHotelId(roomHotelId);
        List<Room> rooms = roomService.getFreeRoomsByRequest(request);
        model.addAttribute("hotel", hotelService.getHotelById(roomHotelId));
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

    @RequestMapping(value = "/comments/{hotelId}")
    public
    @ResponseBody
    List<Comment> getHotelById(@PathVariable int hotelId) {
        return commentService.getCommentsByHotel(hotelId);
    }

    @ModelAttribute("user")
    public User createUser() {
        return new User();
    }

    @ModelAttribute("request")
    public Request createRequest() {
        return new Request();
    }


    @RequestMapping(value = "/saveHotel", method = RequestMethod.POST)
    public
    @ResponseBody
    Hotel saveHotel(@RequestParam("hotel") String hotelJson,
                    @RequestParam(required = false) MultipartFile image,
                    @ModelAttribute User user) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Hotel hotel = objectMapper.readValue(hotelJson, Hotel.class);
            hotel.setUser(user);
            if (image != null && image.getContentType().equals("image/jpeg")) {
                String link = FileUpLoader.uploadImageToImgur(image);
                hotel.setImageLink(link);
            }
            hotelService.saveHotel(hotel);
            return hotelService.getHotelById(hotel.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = "/saveRoom", method = RequestMethod.POST)
    public
    @ResponseBody
    Room saveHotel(@RequestParam("room") String roomJson,
                   @RequestParam(required = false) MultipartFile image) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Room room = objectMapper.readValue(roomJson, Room.class);
            if (image != null && image.getContentType().equals("image/jpeg")) {
                String link = FileUpLoader.uploadImageToImgur(image);
                room.setImageLink(link);
            }
            roomService.saveRoom(room);
            return room;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    @RequestMapping(value = "/search/update")
//    public String updateUser(@RequestParam)



}
