package fr.nine.domain.application.usecase;

import org.springframework.stereotype.Component;

import fr.nine.domain.application.service.auth.IValidateResetTokenPasswordService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ValidateTokenRedefinePasswordUseCase {
  protected final IValidateResetTokenPasswordService validateResetTokenPasswordService;

  public boolean execute(String token) {
    boolean isValidToken = validateResetTokenPasswordService.validateResetToken(token);
    return isValidToken;
  }
}
