package simonov.hotel.services.interfaces;

import org.springframework.stereotype.Service;
import simonov.hotel.entity.Hotel;
import simonov.hotel.entity.Request;

import java.util.List;

@Service
public interface HotelService {

    List<Hotel> getHotels(int first,int limit);

    Hotel getHotelById(int id);

    void saveHotel(Hotel hotel);

    void update(Hotel hotel);

    List<Hotel> getHotelsByUser(int userId, int firstHotel, int limit);

    List<Hotel> getHotelsWithFreeRoom(Request request);

    List<Hotel> getHotelsByName(String name, int cityId, int countryId, int firstResult, int limit);
}
