package fr.nine.domain.application.service.auth;

import fr.nine.domain.enterprise.user.entities.User;

public interface IGenerateTokenRedefinePasswordService {
  String initiatePasswordReset(User user);
}
