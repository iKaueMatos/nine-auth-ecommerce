package fr.nine.domain.application.usecase;

import org.springframework.stereotype.Component;

import fr.nine.domain.application.service.ResetPasswordService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ValidateTokenRedefinePasswordUseCase {
  protected final ResetPasswordService resetPasswordService;

  public boolean execute(String token) {
    boolean isValidToken = resetPasswordService.validateResetToken(token);
    return isValidToken;
  }
}
