package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.interfaces.CountryDAO;
import simonov.hotel.entity.Country;
import simonov.hotel.services.interfaces.CountryService;

import java.util.List;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {
    @Autowired
    CountryDAO countryDAO;

    @Override
    public List<Country> getCountriesByNameCriteria(String name) {
        return countryDAO.getCountriesByNameCriteria(name);
    }

    @Override
    public Country getCountryByName(String name) {
        return countryDAO.getCountryByName(name);
    }

    @Override
    public List<Country> getAllCountries() {
        return countryDAO.getAll();
    }

}
