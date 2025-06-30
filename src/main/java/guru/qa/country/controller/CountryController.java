package guru.qa.country.controller;

import com.fasterxml.jackson.databind.JsonNode;
import guru.qa.country.domain.Country;
import guru.qa.country.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/countries")
public class CountryController {

  CountryService countryService;

  @Autowired
  public CountryController(CountryService countryService) {
    this.countryService = countryService;
  }

  @GetMapping("/all")
  public List<Country> getAll() {
    return countryService.getAllCountries();
  }

  @PostMapping("/add")
  @ResponseStatus(HttpStatus.CREATED)
  public Country addCountry(@Valid @RequestBody Country country) {
    return countryService.addCountry(country);
  }

  @PatchMapping("/{code}")
  public Country updateCountry(@PathVariable("code") String code, @RequestBody JsonNode updateJson) {
    if (code.length() != 2) {
      throw new IllegalArgumentException("code in query path should have length 2");
    }
    return countryService.updateCountryByCode(code, updateJson);
  }
}
