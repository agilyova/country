package guru.qa.country.exeption;

public class DuplicateEntryException extends RuntimeException {
  public DuplicateEntryException(String fieldName, String value) {
    super(String.format("Entity with %s \"%s\" already exists", fieldName, value));
  }
}
