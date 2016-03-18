package simonov.hotel.controllers;

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

import javax.servlet.ServletContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@EnableWebMvc
@SessionAttributes("user")
public class IndexController {

    @Autowired
    HotelService hotelService;
    @Autowired
    UserService userService;
    @Autowired
    RoomService roomService;
    @Autowired
    CityService cityService;
    @Autowired
    CountryService countryService;
    @Autowired
    ConvenienceService convenienceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String printHotels(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("hotels", hotelService.getFirstTenHotels());
//        Request request = new Request();
//        request.setCountryId(1);
//        request.setCityId(1);
//        request.setHotelId(1);
//        request.setStartDate(LocalDate.parse("2016-03-05"));
//        request.setEndDate(LocalDate.parse("2016-05-19"));
//        Map<Integer, Integer> seats = new HashMap<>();
//        seats.put(2, 4);
//        seats.put(1,1);
//        request.setSeats(seats);
//        request.setFirstResult(0);
//        request.setLimit(10);
//        model.addAttribute("hotels", hotelService.getHotelsWithFreeRoom(request));
//        hotelService.getHotelsWithFreeRoom(request).stream().forEach(hotel -> System.out.println(hotel.getRooms().size()));
        return "main";
    }

    @RequestMapping(value = "/city/{name}/{id}")
    public
    @ResponseBody
    String searchCities(@PathVariable String name, @PathVariable int id) {
        List<City> list = cityService.getCitiesByCriteria(name, id);
        JSONArray array = new JSONArray();
        list.stream().forEach(city -> array.add(city.toJSON()));
        return array.toString();
    }

    @RequestMapping(value = "/country/{name}")
    public
    @ResponseBody
    String searchCountry(@PathVariable String name) {
        List<Country> list = countryService.getCountriesByNameCriteria(name);
        JSONArray array = new JSONArray();
        list.stream().forEach(country -> array.add(country.toJSON()));
        return array.toString();
    }

    @RequestMapping(value = "/hotel/{name}/{cityId}/{countryId}")
    public
    @ResponseBody
    String searchHotels(@PathVariable String name, @PathVariable int cityId, @PathVariable int countryId) {
        List<Hotel> list = hotelService.getHotelsByName(name, cityId, countryId);
        JSONArray array = new JSONArray();
        list.stream().forEach(city -> array.add(city.toJSON()));
        return array.toString();
    }


    @RequestMapping(value = "/hotel/{hotelId}")
    public String getHotel(@PathVariable int hotelId, Model model) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        if (hotel != null) {
        model.addAttribute("hotel", hotel);
            List<Room> rooms = roomService.getRoomsByHotel(hotelId);
        model.addAttribute("rooms", rooms);
        return "hotelInfo";
        } else {
            model.addAttribute("message", "Hotel with this ID does not exist");
            return "error";
        }
    }

    @RequestMapping(value = "/hotel/{hotelId}/roomDetails/{roomId}")
    public String roomInfo(@PathVariable int hotelId, @PathVariable int roomId, Model model) {
        Room room = roomService.getRoomById(roomId);
        if (hotelId != room.getHotel().getId()) {
            return "error";
        }
        model.addAttribute("hotel", room.getHotel());
        model.addAttribute("room", room);
        return "roomInfo";
    }


    @RequestMapping(value = "/addRoom", method = RequestMethod.POST)
    public String addRoom(@RequestParam String type,
                          @RequestParam int number,
                          @RequestParam String description,
                          @RequestParam Double price,
                          @RequestParam int seats,
                          @RequestParam int hotelId,
                          @RequestParam MultipartFile imageFile) {
        Room room = new Room();
        room.setType(type);
        room.setPrice(price);
        room.setSeats(seats);
        room.setDescription(description);
        room.setNumber(number);
        room.setHotel(hotelService.getHotelById(hotelId));
        if (imageFile.getContentType().equals("image/jpeg")) {
            room.setImageLink(FileUpLoader.uploadImageToImgur(imageFile));
        }
        roomService.saveRoom(room);

        return "redirect:/hotel/" + hotelId;
    }

    @RequestMapping(value = "/addHotel", method = RequestMethod.POST)
    public String addHotel(@RequestParam String name,
                           @RequestParam("city_id") int cityId,
                           @RequestParam int stars,
                           @RequestParam("convenience") List<Integer> conveniences,
                           @RequestParam MultipartFile imageFile,
                           @ModelAttribute("user") User user) {
        Hotel newHotel = new Hotel();
        newHotel.setName(name);
        newHotel.setCity(cityService.getCityById(cityId));
        newHotel.setStars(stars);
        List<Convenience> convenienceList = new ArrayList<>();
        conveniences.stream().forEach(integer -> convenienceList.add(convenienceService.getConvenienceById(integer)));
        newHotel.setConveniences(convenienceList);
        newHotel.setUser(user);
        if (imageFile.getContentType().equals("image/jpeg")) {
            String link = FileUpLoader.uploadImageToImgur(imageFile);
            newHotel.setImageLink(link);
        }
        hotelService.saveHotel(newHotel);
        return "redirect:/profile";
    }

    @ModelAttribute("user")
    public User createUser() {
        User user = new User();
        user.setRole(Role.NotAuthorized);
        return user;
    }
}
