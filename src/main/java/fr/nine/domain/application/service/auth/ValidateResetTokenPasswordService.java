package fr.nine.domain.application.service.auth;

import fr.nine.domain.enterprise.user.entities.User;

public interface ValidateResetTokenPasswordService {
  boolean validateResetToken(String token);
}
