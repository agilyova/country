package guru.qa.country.controller.graphql;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.country.domain.graphql.CountryGql;
import guru.qa.country.domain.graphql.CountryInputGql;
import guru.qa.country.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CountryMutationController {

  CountryService countryService;

  @Autowired
  public CountryMutationController(CountryService countryService) {
    this.countryService = countryService;
  }

  @MutationMapping
  public CountryGql addCountry(@Argument CountryInputGql input) {
    return countryService.addCountryGql(input);
  }

  @MutationMapping
  public CountryGql updateCountry(@Argument String code, @Argument CountryInputGql input) {
    ObjectMapper om = new ObjectMapper();
    JsonNode jsonNode = om.valueToTree(input);
    return countryService.updateCountryByCodeGql(code, jsonNode);
  }
}