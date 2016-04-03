package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.Country;

import java.util.List;

public interface CountryDAO extends GenericDAO<Country, Integer> {
    List<Country> getCountriesByNameCriteria(String nameCriteria, int firstResult, int limit);
}
