package fr.nine.infra.controller.http.auth.register;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.nine.domain.application.payload.request.RegisterRequest;
import fr.nine.domain.application.payload.response.AuthenticationResponse;
import fr.nine.domain.application.service.auth.IAuthenticationService;
import fr.nine.domain.application.service.auth.IJwtService;
import fr.nine.domain.application.service.auth.IRefreshTokenService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(name = "/api/v1/auth")
public class RegisterUserController {
  private final IAuthenticationService authenticationService;
  private final IRefreshTokenService refreshTokenService;
  private final IJwtService jwtService;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> handle(@Valid @RequestBody RegisterRequest request) {
    AuthenticationResponse authenticationResponse = authenticationService.register(request);
    ResponseCookie jwtCookie = jwtService.generateJwtCookie(authenticationResponse.getAccessToken());
    ResponseCookie refreshTokenCookie = refreshTokenService
        .generateRefreshTokenCookie(authenticationResponse.getRefreshToken());
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
        .body(authenticationResponse);
  }
}
