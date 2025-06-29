package guru.qa.country.domain;

import guru.qa.country.data.CountryEntity;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record Country(
  @NotBlank(message = "Name can not be blank")
  String name,
  @NotBlank(message = "Code can not be blank")
  @Size(min = 2, max = 2, message = "Code length should be 2 characters")
  String code
) {

  public static @Nonnull Country fromEntity(@Nonnull CountryEntity entity) {
    return new Country(entity.getName(), entity.getCode());
  }
}