package fr.nine.domain.application.service.auth;

public interface IValidateResetTokenPasswordService {
  boolean validateResetToken(String token);
}
