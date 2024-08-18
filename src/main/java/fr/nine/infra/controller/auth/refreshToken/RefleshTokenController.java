package fr.nine.infra.controller.auth.refreshToken;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.nine.domain.application.payload.request.RefreshTokenRequest;
import fr.nine.domain.application.payload.response.RefreshTokenResponse;
import fr.nine.domain.application.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(name = "/api/v1/auth")
public class RefleshTokenController {
  private final RefreshTokenService refreshTokenService;
  
  @PostMapping("/refresh-token")
  public ResponseEntity<RefreshTokenResponse> handle(@RequestBody RefreshTokenRequest request) {
    return ResponseEntity.ok(refreshTokenService.generateNewToken(request));
  }
}
