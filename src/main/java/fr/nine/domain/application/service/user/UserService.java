package fr.nine.domain.application.service.user;

public interface UserService {
  void updatePassword(String email, String newPassword);
}
