package guru.qa.country.service;

import com.fasterxml.jackson.databind.JsonNode;
import guru.qa.country.domain.Country;
import guru.qa.country.domain.graphql.CountryGql;
import guru.qa.country.domain.graphql.CountryInputGql;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CountryService {

  List<Country> getAllCountries();

  List<CountryGql> getAllGqlCountries();

  Page<CountryGql> getAllGqlCountries(Pageable pageable);

  Country addCountry(Country country);

  CountryGql addCountryGql(CountryInputGql country);

  Country updateCountryByCode(String code, JsonNode updateJson);

  CountryGql updateCountryByCodeGql(String code, JsonNode updateJson);
}
