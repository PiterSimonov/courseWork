package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.City;

import java.util.List;

public interface CityDAO extends GenericDAO<City, Integer> {

    List<City> getCitiesByCriteria(String cityName, int firstResult, int limit);

    List<City> getCitiesByCountryId(Integer countryId, int firstResult, int limit);

    List<City> getCitiesByCountryAndName(String cityName, Integer countryId, int firstResult, int limit);
}
