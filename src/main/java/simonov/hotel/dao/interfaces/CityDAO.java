package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.City;

import java.util.List;

public interface CityDAO extends GenericDAO<City, Integer> {

    City getCityByName(String cityName);

    List<City> getCitiesByCriteria(String cityName);

    List<City> getCitiesByCountryId(Integer countryId);

    List<City> getCitiesByCountryAndName(String cityName, Integer countryId);
}
