package simonov.hotel.services.interfaces;

import org.springframework.stereotype.Service;
import simonov.hotel.entity.Country;

import java.util.List;

@Service
public interface CountryService {

    List<Country> getCountriesByNameCriteria(String name);

    List<Country> getAllCountries();
}
