package simonov.hotel.services.interfaces;

import org.springframework.stereotype.Service;
import simonov.hotel.entity.City;

import java.util.List;

@Service
public interface CityService {
    List<City> getCitiesByCriteria(String city, Integer countryId, int firstResult, int limit);

    List<City> getCitiesByCountryId(Integer countryId, int firstResult, int limit);
}
