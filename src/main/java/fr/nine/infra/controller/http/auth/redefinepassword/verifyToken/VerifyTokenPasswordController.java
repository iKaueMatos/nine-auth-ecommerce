package fr.nine.infra.controller.http.auth.redefinepassword.verifyToken;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.nine.domain.application.usecase.ValidateTokenRedefinePasswordUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;

@RestController
@SecurityRequirements()
@RequiredArgsConstructor
@RequestMapping(name = "/api/v1/auth")
public class VerifyTokenPasswordController {
  private final ValidateTokenRedefinePasswordUseCase validateTokenRedefinePasswordUseCase;

  @PostMapping("/verify-token")
  public ResponseEntity<String> handle(@RequestParam String token) {
    try {
      boolean isValid = validateTokenRedefinePasswordUseCase.execute(token);
      if (isValid) return ResponseEntity.ok("Token verificado com sucesso!");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
    } catch (UsernameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a solicitação.");
    }
  }
}
