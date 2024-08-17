package fr.nine.domain.application.exception;
import java.lang.RuntimeException;

public class InvalidTokenException extends RuntimeException {
  public InvalidTokenException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidTokenException(String message) {
    super(message);
  }
}
