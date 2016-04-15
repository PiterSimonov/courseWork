package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.interfaces.HotelDAO;
import simonov.hotel.entity.Hotel;
import simonov.hotel.entity.Role;
import simonov.hotel.entity.SearchRequest;
import simonov.hotel.services.interfaces.HotelService;

import java.util.List;

@Service
@Transactional
public class HotelServiceImpl implements HotelService {

    @Autowired
    HotelDAO hotelDAO;

    @Override
    public List<Hotel> getHotels(int first, int limit) {
        return hotelDAO.getHotels(first, limit);
    }

    @Override
    public Hotel getHotelById(int id) {
        return hotelDAO.get(id);
    }

    @Override
    public void saveHotel(Hotel hotel) {
        if (hotel.getUser().getRole() == Role.HotelOwner) {
            hotelDAO.save(hotel);
        }
    }

    @Override
    public void update(Hotel hotel) {
        hotelDAO.update(hotel);
    }

    @Override
    public List<Hotel> getHotelsByUser(int userId, int firstHotel, int limit) {
        return hotelDAO.getHotelsByUser(userId, firstHotel, limit);
    }

    @Override
    public List<Hotel> getHotelsWithFreeRoom(SearchRequest searchRequest) {
        int roomsCount = searchRequest.getSeats().values().stream().reduce(0, (sum, element) -> sum + element);
        if (roomsCount > 8) {
            return null;
        }
        if (searchRequest.getCountryId() == 0 && searchRequest.getCityId() == 0 && searchRequest.getHotelId() == 0) {
            return null;
        }
        return hotelDAO.getHotelsWithFreeRoom(searchRequest);
    }

    @Override
    public List<Hotel> getHotelsByName(String name, int cityId, int countryId, int firstResult, int limit) {
        return hotelDAO.getHotelsByCriteria(countryId, cityId, name, firstResult, limit);
    }
}
