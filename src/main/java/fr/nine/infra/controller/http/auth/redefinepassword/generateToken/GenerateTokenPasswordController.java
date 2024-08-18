package fr.nine.infra.controller.http.auth.redefinepassword.generateToken;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.nine.domain.application.usecase.GenerateTokenRedefinePasswordUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;

@RestController
@SecurityRequirements()
@RequiredArgsConstructor
@RequestMapping(name = "/api/v1/auth")
public class GenerateTokenPasswordController {
  private final GenerateTokenRedefinePasswordUseCase generateTokenRedefinePasswordUseCase;

  @PostMapping("/generate-token")
  public ResponseEntity<String> handle(@RequestParam String email) {
    try {
      generateTokenRedefinePasswordUseCase.execute(email);
      return ResponseEntity.ok("Email de verificação enviado com sucesso!");
    } catch (UsernameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request.");
    }
  }
}
