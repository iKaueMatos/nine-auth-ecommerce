package fr.nine.domain.enterprise.user.service.user;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.nine.domain.application.service.user.UserService;
import fr.nine.domain.enterprise.user.entities.User;
import fr.nine.infra.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceIml implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public void updatePassword(String email, String newPassword) {
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
    String encodedPassword = passwordEncoder.encode(newPassword);
    user.setPassword(encodedPassword);
    userRepository.save(user);
  }
}
