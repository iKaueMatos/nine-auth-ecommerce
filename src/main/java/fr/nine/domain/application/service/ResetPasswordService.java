package fr.nine.domain.application.service;

import fr.nine.domain.enterprise.user.entities.User;

public interface ResetPasswordService {
  String initiatePasswordReset(User user);
  boolean validateResetToken(String token);
}
