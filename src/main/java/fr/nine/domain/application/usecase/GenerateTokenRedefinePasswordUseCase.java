package fr.nine.domain.application.usecase;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import fr.nine.domain.application.service.auth.GenerateTokenRedefinePasswordService;
import fr.nine.domain.application.service.auth.ValidateResetTokenPasswordService;
import fr.nine.domain.enterprise.user.entities.User;
import fr.nine.infra.repository.UserRepository;
import fr.nine.infra.service.sendEmail.EmailService;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GenerateTokenRedefinePasswordUseCase {
  private final UserRepository userRepository;
  protected final GenerateTokenRedefinePasswordService generateTokenRedefinePasswordService;
  private final EmailService emailService;

  public void execute(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String token = generateTokenRedefinePasswordService.initiatePasswordReset(user);
            emailService.sendResetPasswordEmail(email, token);
        } else {
            throw new UsernameNotFoundException("O usuario com este" + email + " não foi encontrado.");
        }
  }
}
