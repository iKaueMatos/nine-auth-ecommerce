package fr.nine.domain.enterprise.user.service.auth.ResetPasswordServiceImpl;

import org.springframework.stereotype.Service;

import fr.nine.domain.application.exception.PasswordResetException;
import fr.nine.domain.application.service.auth.ValidateResetTokenPasswordService;
import fr.nine.infra.provider.generateNumericToken.NumericTokenProvider;
import fr.nine.infra.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ValidateResetTokenPasswordServiceImpl implements ValidateResetTokenPasswordService {
  private final UserRepository userRepository;
  private final NumericTokenProvider numericTokenProvider;

  public boolean validateResetToken(String token) {
    try {
      return userRepository.findByResetPasswordToken(token)
          .filter(user -> !numericTokenProvider.isTokenExpired(user.getResetTokenExpiryDate()))
          .isPresent();
    } catch (Exception exception) {
      throw new PasswordResetException("Erro ao validar o token de redefinição.", exception);
    }
  }
}
