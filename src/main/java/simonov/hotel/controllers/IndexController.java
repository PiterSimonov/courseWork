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

import javax.servlet.ServletContext;
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
    BookingService bookingService;
    @Autowired
    CommentService commentService;
    @Autowired
    ServletContext servletContext;
    @Autowired
    CityService cityService;
    @Autowired
    OrderService orderService;
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

    @RequestMapping(value = "/comment/{hotelId}")
    public String saveComment(@PathVariable int hotelId, @ModelAttribute User user) {
        Comment comment = new Comment();
        comment.setHotel(hotelService.getHotelById(hotelId));
        comment.setRating(4d);
        comment.setComment("Bad");
        comment.setUser(user);
        commentService.save(comment);
        return "userProfile";
    }

    @RequestMapping(value = "/addRoom", method = RequestMethod.POST)
    public String addRoom(@RequestParam String type,
                          @RequestParam int number,
                          @RequestParam String description,
                          @RequestParam Double price,
                          @RequestParam int seats,
                          @RequestParam int hotelId,
                          @RequestParam MultipartFile image) {
        Room room = new Room();
        room.setType(type);
        room.setPrice(price);
        room.setSeats(seats);
        room.setDescription(description);
        room.setNumber(number);
        Hotel currentHotel = hotelService.getHotelById(hotelId);
        room.setHotel(currentHotel);
        if (image.getContentType().equals("image/jpeg")) {
//            String subPath = servletContext.getRealPath("/resources/images/rooms/") + currentHotel.getName() + room.getId() + ".jpg";
//            FileUpLoader.uploadImageToServer(image, subPath);
            room.setImageLink(FileUpLoader.uploadImageToImgur(image));
        }
        roomService.saveRoom(room);

        return "redirect:/hotel/" + currentHotel.getId();
    }

    @RequestMapping(value = "/addHotel", method = RequestMethod.POST)
    public String addHotel(@RequestParam String name,
                           @RequestParam String city,
                           @RequestParam int stars,
                           @RequestParam int owner,
                           @RequestParam MultipartFile image) {
        Hotel newHotel = new Hotel();
        newHotel.setName(name);
        newHotel.setCity(cityService.getCityByName(city));
        newHotel.setStars(stars);
        newHotel.setUser(userService.get(owner));
        if (image.getContentType().equals("image/jpeg")) {
            String link = FileUpLoader.uploadImageToImgur(image);
//            String path = servletContext.getRealPath("/resources/images/hotels/") + newHotel.getId() + ".jpg";
//            FileUpLoader.uploadImageToServer(image, path);
            newHotel.setImageLink(link);
        }
        hotelService.saveHotel(newHotel);
        return "redirect:/profile";
    }

    @RequestMapping("/profile")
    public String userProfile(@ModelAttribute User user, Model model) {
        if (user.getRole() == Role.HotelOwner) {
            List<Hotel> hotels = hotelService.getHotelsByUser(user.getId());
            model.addAttribute("hotels", hotels);
            return "hotelsOwner";
        } else if (user.getRole() == Role.CLIENT) {
            List<Order> orders = orderService.getOrdersByUser(user.getId());
            model.addAttribute("orders", orders);
            return "userReservation";
        } else return "redirect:/";
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
