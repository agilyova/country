package guru.qa.country.service;

import com.fasterxml.jackson.databind.JsonNode;
import guru.qa.country.domain.Country;

import java.util.List;

public interface CountryService {

  List<Country> getAllCountries();

  Country addCountry(Country country);

  Country updateCountryByCode(String code, JsonNode updateJson);
}
