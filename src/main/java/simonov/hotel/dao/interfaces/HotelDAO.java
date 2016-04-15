package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.Hotel;
import simonov.hotel.entity.SearchRequest;

import java.util.List;

public interface HotelDAO extends GenericDAO<Hotel, Integer> {

    List<Hotel> getHotelsByUser(Integer userId, int firstHotel, int limit);

    List<Hotel> getHotelsByCriteria(Integer countryId, Integer cityId, String hotelName, int firstResult, int limit);

    List<Hotel> getHotelsWithFreeRoom(SearchRequest searchRequest);

    List<Hotel> getHotels(int first,int limit);

}
