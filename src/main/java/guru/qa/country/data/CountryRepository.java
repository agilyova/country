package guru.qa.country.data;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<CountryEntity, UUID> {
  @Nonnull
  Optional<CountryEntity> findByCode(@Nonnull String code);

  boolean existsByName(String name);

  boolean existsByCode(String code);

  boolean existsByNameAndIdNot(String name, UUID id);

  boolean existsByCodeAndIdNot(String code, UUID id);
}
