package simonov.hotel.controllers;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import simonov.hotel.entity.*;
import simonov.hotel.services.interfaces.*;
import simonov.hotel.utilites.FileUpLoader;

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
    BookingService bookingService;
    @Autowired
    CityService cityService;
    @Autowired
    CountryService countryService;
    @Autowired
    ConvenienceService convenienceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String printHotels(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("hotels", hotelService.getFirstTenHotels());
        return "main";
    }
    @RequestMapping(value = "/error",  method = RequestMethod.GET)
    public String error(@RequestParam String message, Model model) {
        model.addAttribute("message", message);
        return "error";
    }


    @RequestMapping(value = "/hotel/{hotelId}")
    public String getHotel(@PathVariable int hotelId, Model model) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        if (hotel != null) {
            model.addAttribute("hotel", hotel);
            List<Room> rooms = roomService.getRoomsByHotel(hotelId);
            model.addAttribute("rooms", rooms);
            model.addAttribute("services", convenienceService.getAll());
            return "hotelInfo";
        } else {
            model.addAttribute("message", "Hotel with this ID does not exist");
            return "error";
        }
    }

    @RequestMapping(value = "/hotel/{hotelId}/edit", method = RequestMethod.POST)
    public
    @ResponseBody
    boolean updateHotel(@PathVariable int hotelId,
                        @RequestParam String name,
                        @RequestParam int stars,
                        @RequestParam("convenience") List<Integer> conveniences,
                        @RequestParam MultipartFile imageFile) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        List<Convenience> convenienceList = new ArrayList<>();
        conveniences.stream().forEach(integer -> convenienceList.add(convenienceService.getConvenienceById(integer)));
        hotel.setConveniences(convenienceList);
        hotel.setName(name);
        hotel.setStars(stars);
        if (!imageFile.isEmpty() && imageFile.getContentType().equals("image/jpeg")) {
            String link = FileUpLoader.uploadImageToImgur(imageFile);
            hotel.setImageLink(link);
        }
        hotelService.update(hotel);
        return true;
    }

    @RequestMapping(value = "room/{roomId}/edit", method = RequestMethod.POST)
    public
    @ResponseBody
    Room updateRoom(@PathVariable int roomId,
                    @RequestParam String type,
                    @RequestParam int price,
                    @RequestParam String description,
                    @RequestParam int seats,
                    @RequestParam MultipartFile imageFile
    ) {
        System.out.println("Inside room edit " + type + " price:" + price);
        Room room = roomService.getRoomById(roomId);
        room.setType(type);
        room.setPrice(price);
        room.setDescription(description);
        room.setSeats(seats);
        if (imageFile.getContentType().equals("image/jpeg")) {
            room.setImageLink(FileUpLoader.uploadImageToImgur(imageFile));
        }
        roomService.update(room);
        return room;
    }

    @RequestMapping(value = "/hotel/{hotelId}/roomDetails/{roomId}")
    public String roomInfo(@PathVariable int hotelId, @PathVariable int roomId, Model model) {
        Room room = roomService.getRoomById(roomId);
        if (hotelId != room.getHotel().getId()) {
            model.addAttribute("message", "This room is not in this hotel");
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
                          @RequestParam int price,
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

    @RequestMapping(value = "/addHotel", method = RequestMethod.POST) //TODO this must be ajax request
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

    @RequestMapping("hotel/{hotelId}/getBooking")
    public String getBooking(@PathVariable int hotelId,
                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
                             @RequestParam(required = false) Integer roomNumber, @ModelAttribute User user, Model model) {
        if (hotelService.getHotelById(hotelId).getUser().getId() == user.getId()) {
            List<Booking> bookings = bookingService.getBookingByCriteria(hotelId, fromDate, toDate, roomNumber != null ? roomNumber : 0);
            model.addAttribute("bookings", bookings);
            return "booking";
        } else {
            model.addAttribute("message", "You are not the owner of this hotel");
            return "error";
        }
    }

    @ModelAttribute("user")
    public User createUser() {
        User user = new User();
        user.setRole(Role.NotAuthorized);
        return user;
    }
}
