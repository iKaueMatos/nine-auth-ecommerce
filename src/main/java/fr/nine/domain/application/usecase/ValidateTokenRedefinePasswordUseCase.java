package fr.nine.domain.application.usecase;

import org.springframework.stereotype.Component;

import fr.nine.domain.application.service.auth.ValidateResetTokenPasswordService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ValidateTokenRedefinePasswordUseCase {
  protected final ValidateResetTokenPasswordService validateResetTokenPasswordService;

  public boolean execute(String token) {
    boolean isValidToken = validateResetTokenPasswordService.validateResetToken(token);
    return isValidToken;
  }
}
