package guru.qa.country.domain.graphql;

import guru.qa.country.data.CountryEntity;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CountryGql(
  UUID id,
  @NotBlank(message = "Name can not be blank")
  String name,
  @NotBlank(message = "Code can not be blank")
  @Size(min = 2, max = 2, message = "Code length should be 2 characters")
  String code
) {

  public static @Nonnull CountryGql fromEntity(@Nonnull CountryEntity entity) {
    return new CountryGql(entity.getId(), entity.getName(), entity.getCode());
  }
}