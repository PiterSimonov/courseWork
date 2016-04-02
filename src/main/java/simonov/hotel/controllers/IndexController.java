package simonov.hotel.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    RoomService roomService;
    @Autowired
    BookingService bookingService;
    @Autowired
    CityService cityService;
    @Autowired
    ConvenienceService convenienceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String printHotels(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("hotels", hotelService.getHotels(0,10));
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
            //TODO select selected services
            return "hotelInfo";
        } else {
            model.addAttribute("message", "Hotel with this ID does not exist");
            return "error";
        }
    }

    @RequestMapping(value = "/hotel/{hotelId}/edit", method = RequestMethod.POST)
    public
    @ResponseBody
    String updateHotel(@PathVariable int hotelId,
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
        return hotel.getImageLink();
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

    @RequestMapping(value = "/saveRoom", method = RequestMethod.POST)
    public
    @ResponseBody
    Room saveRoom(@RequestParam("room") String roomJson,
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

    @RequestMapping(value = "/check-room-number", method = RequestMethod.GET)
    public @ResponseBody boolean checkRoomNumber(@RequestParam int number, @RequestParam int hotelId){
        return roomService.roomNumberIsFree(number,hotelId);
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
        return new User();
    }
}
