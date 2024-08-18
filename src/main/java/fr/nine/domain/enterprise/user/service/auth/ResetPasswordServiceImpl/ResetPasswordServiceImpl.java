package fr.nine.domain.enterprise.user.service.auth.ResetPasswordServiceImpl;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.nine.domain.application.exception.PasswordResetException;
import fr.nine.domain.application.service.ResetPasswordService;
import fr.nine.domain.enterprise.user.entities.User;
import fr.nine.infra.provider.NumericTokenProvider;
import fr.nine.infra.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ResetPasswordServiceImpl implements ResetPasswordService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final NumericTokenProvider numericTokenProvider;

  public String initiatePasswordReset(User user) {
    try {
      String token = numericTokenProvider.generateNumericToken();
      LocalDateTime expiryDate = numericTokenProvider.calculateExpiryDate();

      user.setResetPasswordToken(token);
      user.setResetTokenExpiryDate(expiryDate);
      userRepository.save(user);
      return token;
    } catch (Exception exception) {
      throw new PasswordResetException("Erro ao iniciar o processo de redefinição de senha.", exception);
    }
  }

  public boolean validateResetToken(String token) {
    try {
      return userRepository.findByResetPasswordToken(token)
          .filter(user -> !numericTokenProvider.isTokenExpired(user.getResetTokenExpiryDate()))
          .isPresent();
    } catch (Exception exception) {
      throw new PasswordResetException("Erro ao validar o token de redefinição.", exception);
    }
  }

  public void updatePassword(String email, String newPassword) {
      User user = userRepository.findByEmail(email)
              .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
      String encodedPassword = passwordEncoder.encode(newPassword);
      user.setPassword(encodedPassword);
      userRepository.save(user);
  }
}
