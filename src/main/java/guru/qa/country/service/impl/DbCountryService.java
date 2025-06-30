package guru.qa.country.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.country.data.CountryEntity;
import guru.qa.country.data.CountryRepository;
import guru.qa.country.domain.Country;
import guru.qa.country.domain.graphql.CountryGql;
import guru.qa.country.domain.graphql.CountryInputGql;
import guru.qa.country.exeption.DuplicateEntryException;
import guru.qa.country.service.CountryService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class DbCountryService implements CountryService {

  private final CountryRepository countryRepository;
  private final ObjectMapper objectMapper;

  @Autowired
  public DbCountryService(CountryRepository countryRepository, ObjectMapper objectMapper) {
    this.countryRepository = countryRepository;
    this.objectMapper = objectMapper;
  }

  @Override
  public List<Country> getAllCountries() {
    return countryRepository.findAll()
      .stream()
      .map(Country::fromEntity)
      .toList();
  }

  @Override
  public Page<CountryGql> getAllGqlCountries(Pageable pageable) {
    return countryRepository.findAll(pageable)
      .map(CountryGql::fromEntity);
  }

  @Override
  public Country addCountry(Country country) {
    verifyOnDuplicates(country, null);
    return Country.fromEntity(this.save(country));
  }

  @Override
  public CountryGql addCountryGql(CountryInputGql country) {
    verifyOnDuplicates(country, null);
    return CountryGql.fromEntity(this.save(country));
  }

  @Override
  public Country updateCountryByCode(String code, JsonNode updateJson) {
    CountryEntity ce = countryRepository.findByCode(code).orElseThrow();
    try {
      objectMapper.readerForUpdating(ce).readValue(updateJson.toString());
    } catch (IOException e) {
      throw new RuntimeException("Invalid json", e);
    }
    verifyOnDuplicates(Country.fromEntity(ce), ce.getId());
    return Country.fromEntity(countryRepository.save(ce));
  }

  @Override
  public CountryGql updateCountryByCodeGql(String code, JsonNode updateJson) {
    CountryEntity ce = countryRepository.findByCode(code).orElseThrow();
    try {
      objectMapper.readerForUpdating(ce).readValue(updateJson.toString());
    } catch (IOException e) {
      throw new RuntimeException("Invalid json", e);
    }
    verifyOnDuplicates(Country.fromEntity(ce), ce.getId());
    return CountryGql.fromEntity(countryRepository.save(ce));
  }

  private CountryEntity save(@Nonnull Country country) {
    String name = country.name().trim();
    String code = country.code().trim().toUpperCase();

    CountryEntity ce = new CountryEntity();
    ce.setName(name);
    ce.setCode(code);
    return countryRepository.save(ce);
  }

  private CountryEntity save(@Nonnull CountryInputGql country) {
    return save(Country.fromCountryInputGql(country));
  }

  private void verifyOnDuplicates(Country country, UUID currentId) {
    if (currentId != null) {
      if (countryRepository.existsByNameAndIdNot(country.name(), currentId)) {
        throw new DuplicateEntryException("name", country.name());
      }
      if (countryRepository.existsByCodeAndIdNot(country.code(), currentId)) {
        throw new DuplicateEntryException("code", country.code());
      }
    } else {
      if (countryRepository.existsByName(country.name())) {
        throw new DuplicateEntryException("name", country.name());
      }
      if (countryRepository.existsByCode(country.code())) {
        throw new DuplicateEntryException("code", country.code());
      }
    }
  }

  private void verifyOnDuplicates(CountryInputGql country, UUID currentId) {
    verifyOnDuplicates(Country.fromCountryInputGql(country), currentId);
  }
}
