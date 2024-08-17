package fr.nine.domain.enterprise.user.service;

import java.util.Optional;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import fr.nine.domain.application.exception.PasswordResetException;
import fr.nine.domain.application.exception.TokenExpiredException;
import fr.nine.domain.application.service.ResetPasswordService;
import fr.nine.domain.enterprise.user.entities.User;
import fr.nine.infra.provider.NumericTokenProvider;
import fr.nine.infra.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ResetPasswordServiceImpl implements ResetPasswordService {

    private final UserRepository userRepository;
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
          throw new PasswordResetException("Error initiating password reset process.", exception);
      }
    }

    public boolean validateResetToken(String token) {
        try {
            Optional<User> userOptional = userRepository
                                                  .findByResetPasswordToken(token);

            User user = userOptional.get();
            if (numericTokenProvider.isTokenExpired(user.getResetTokenExpiryDate())
                && userOptional.isPresent()
            ) {
                throw new TokenExpiredException("The reset token has expired.");
            }

            return true;
        } catch (Exception exception) {
            throw new PasswordResetException("Error validating reset token.", exception);
        }
    }
}
