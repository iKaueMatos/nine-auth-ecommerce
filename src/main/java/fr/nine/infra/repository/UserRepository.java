package fr.nine.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.nine.domain.enterprise.user.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
  Optional<User> findByResetPasswordToken(String resetPasswordToken);
}
