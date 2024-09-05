package fr.nine.domain.enterprise.user.service.auth.generateToken;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import fr.nine.domain.application.exception.PasswordResetException;
import fr.nine.domain.application.service.auth.IGenerateTokenRedefinePasswordService;
import fr.nine.domain.enterprise.user.entities.User;
import fr.nine.infra.provider.generateNumericToken.NumericTokenProvider;
import fr.nine.infra.repository.UserRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class GenerateTokenRedefinePasswordServiceImpl implements IGenerateTokenRedefinePasswordService {
  private final NumericTokenProvider numericTokenProvider;
  private final UserRepository userRepository;

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
}
