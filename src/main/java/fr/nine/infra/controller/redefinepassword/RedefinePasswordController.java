package fr.nine.infra.controller.redefinepassword;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.nine.domain.application.usecase.RedefinePasswordUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;

@RestController
@SecurityRequirements()
@RequiredArgsConstructor
@RequestMapping(name = "/api/v1/auth")
public class RedefinePasswordController {
  private final RedefinePasswordUseCase redefinePasswordUseCase;

  @PostMapping("/redefine-password")
  public ResponseEntity<String> handle(@RequestParam String email) {
      try {
          redefinePasswordUseCase.generateTokenUser(email);
          return ResponseEntity.ok("Email de verificação enviado com sucesso!");
      } catch (UsernameNotFoundException e) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request.");
      }
  }
}
