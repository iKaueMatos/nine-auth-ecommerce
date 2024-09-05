package fr.nine.infra.controller.http.auth.logout;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.nine.domain.application.service.auth.IJwtService;
import fr.nine.domain.application.service.auth.IRefreshTokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@SecurityRequirements()
@RequiredArgsConstructor
@RequestMapping(name = "/api/v1/auth")
@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
public class LogoutController {
  private final IRefreshTokenService refreshTokenService;
  private final IJwtService jwtService;

  @PostMapping("/logout")
  public ResponseEntity<Void> handle(HttpServletRequest request) {
    String refreshToken = refreshTokenService.getRefreshTokenFromCookies(request);
    if (refreshToken != null) {
      refreshTokenService.deleteByToken(refreshToken);
    }
    ResponseCookie jwtCookie = jwtService.getCleanJwtCookie();
    ResponseCookie refreshTokenCookie = refreshTokenService.getCleanRefreshTokenCookie();
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
        .build();
  }
}
