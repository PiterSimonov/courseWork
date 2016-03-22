package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.interfaces.HotelDAO;
import simonov.hotel.entity.Hotel;
import simonov.hotel.entity.Request;
import simonov.hotel.entity.Role;
import simonov.hotel.services.interfaces.HotelService;

import java.util.List;

@Service
@Transactional
public class HotelServiceImpl implements HotelService {

    @Autowired
    HotelDAO hotelDAO;

    @Override
    public List<Hotel> getFirstTenHotels() {
        return hotelDAO.getFirstTenHotels();
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
    public List<Hotel> getHotelsByUser(int userId) {
        return hotelDAO.getHotelsByUser(userId);
    }

    @Override
    public List<Hotel> getHotelsByCountry(int countryId) {
        return hotelDAO.getHotelsByCountry(countryId);
    }

    @Override
    public List<Hotel> getHotelsByCity(int city) {
        return hotelDAO.getHotelsByCity(city);
    }

    @Override
    public List<Hotel> getHotelsWithFreeRoom(Request request) {
        if (request.getCountryId() == 0 && request.getCityId() == 0 && request.getHotelId() == 0) {
            return null;
        } else if (request.getHotelId() != 0) {
            request.setCountryId(0);
            request.setCityId(0);
        } else if (request.getCityId() != 0) {
            request.setCountryId(0);
            request.setHotelId(0);
        } else {
            request.setCityId(0);
            request.setHotelId(0);
        }
        return hotelDAO.getHotelsWithFreeRoom(request);
    }

    @Override
    public List<Hotel> getHotelsByName(String name, int cityId, int countryId) {
        return hotelDAO.getHotelsByCriteria(countryId, cityId, name);
    }
}
