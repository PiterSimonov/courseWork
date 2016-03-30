package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.Hotel;
import simonov.hotel.entity.Request;

import java.util.List;

public interface HotelDAO extends GenericDAO<Hotel, Integer> {

    List<Hotel> getHotelsByUser(Integer userId);

    List<Hotel> getHotelsByCriteria(Integer countryId, Integer cityId, String hotelName);

    List<Hotel> getHotelsWithFreeRoom(Request request);

    List<Hotel> getFirstTenHotels();

}
