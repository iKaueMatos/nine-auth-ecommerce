package fr.nine.domain.application.service.auth;

import fr.nine.domain.enterprise.user.entities.User;

public interface GenerateTokenRedefinePasswordService {
  String initiatePasswordReset(User user);
}
