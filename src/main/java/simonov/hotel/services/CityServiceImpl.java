package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.interfaces.CityDAO;
import simonov.hotel.entity.City;
import simonov.hotel.services.interfaces.CityService;

import java.util.List;

@Service
@Transactional
public class CityServiceImpl implements CityService {
    @Autowired
    CityDAO cityDAO;

    @Override
    public List<City> getCitiesByCriteria(String city, Integer countryId) {
        List<City> cityList;
        if (countryId != null && countryId > 0) {
            cityList = cityDAO.getCitiesByCountryAndName(city, countryId);
        } else {
            cityList = cityDAO.getCitiesByCriteria(city);
        }
        return cityList;
    }

    @Override
    public City getCityById(Integer cityId) {
        return cityDAO.get(cityId);
    }

    @Override
    public List<City> getCitiesByCountryId(Integer countryId) {
        return cityDAO.getCitiesByCountryId(countryId);
    }
}
