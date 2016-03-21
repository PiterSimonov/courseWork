package simonov.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import simonov.hotel.entity.*;
import simonov.hotel.services.interfaces.*;
import simonov.hotel.utilites.FileUpLoader;

import java.util.ArrayList;
import java.util.List;

@Controller
@EnableWebMvc
@SessionAttributes(types = User.class)
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
    ConvenienceService convenienceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String printHotels(@ModelAttribute User user, @ModelAttribute ArrayList<Hotel> hotels, Model model) {
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

    @RequestMapping(value = "/city")
    public @ResponseBody String searchCities(){
        return " [\n" +
                "     { \"name\": \"home\", \"id\": \"212 555-1234\" },\n" +
                "     { \"name\": \"fax\", \"id\": \"646 555-4567\" }\n" +
                " ]";
    }


    @RequestMapping(value = "/hotel/{id}")
    public String searchHotel(@PathVariable int id, Model model, @ModelAttribute("user") User user,
                              @ModelAttribute("hotels") ArrayList<Hotel> hotels) {
        Hotel hotel = hotelService.getHotelById(id);
        model.addAttribute("hotel", hotel);
        List<Room> rooms = roomService.getRoomsByHotel(id);
        model.addAttribute("rooms", rooms);
        return "hotelInfo";
    }

    @RequestMapping(value = "/hotel/{hotelId}/roomDetails/{roomId}")
    public String roomInfo(@PathVariable int hotelId, @PathVariable int roomId, Model model, @ModelAttribute User user) {
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
                           @RequestParam MultipartFile imageFile, @ModelAttribute User user) {
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

    @ModelAttribute
    public Room createRoom() {
        return new Room();
    }
}
