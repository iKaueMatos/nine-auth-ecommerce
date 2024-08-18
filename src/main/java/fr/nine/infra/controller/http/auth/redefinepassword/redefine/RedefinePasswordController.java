package fr.nine.infra.controller.http.auth.redefinepassword.redefine;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.nine.domain.application.payload.request.PasswordUpdateRequest;
import fr.nine.domain.application.payload.response.PasswordUpdateResponse;
import fr.nine.domain.application.service.user.UserService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;

@RestController
@SecurityRequirements()
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class RedefinePasswordController {
  private final UserService resetPasswordService;

  @PostMapping("/update-password")
  public ResponseEntity<PasswordUpdateResponse> handle(@RequestBody PasswordUpdateRequest request) {
    try {
        resetPasswordService.updatePassword(request.getEmail(), request.getNewPassword());
        return ResponseEntity.ok(new PasswordUpdateResponse("Senha atualizada com sucesso!"));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new PasswordUpdateResponse("Erro ao atualizar a senha."));
    }
  }
}
