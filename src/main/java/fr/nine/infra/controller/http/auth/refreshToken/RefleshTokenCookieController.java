package fr.nine.infra.controller.http.auth.refreshToken;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.nine.domain.application.payload.request.RefreshTokenRequest;
import fr.nine.domain.application.payload.response.RefreshTokenResponse;
import fr.nine.domain.application.service.auth.IJwtService;
import fr.nine.domain.application.service.auth.IRefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(name = "/api/v1/auth")
public class RefleshTokenCookieController {
  private final IJwtService jwtService;
  private final IRefreshTokenService refreshTokenService;

  @PostMapping("/refresh-token-cookie")
  public ResponseEntity<Void> handle(HttpServletRequest request) {
    String refreshToken = refreshTokenService.getRefreshTokenFromCookies(request);
    RefreshTokenResponse refreshTokenResponse = refreshTokenService
        .generateNewToken(new RefreshTokenRequest(refreshToken));
    ResponseCookie NewJwtCookie = jwtService.generateJwtCookie(refreshTokenResponse.getAccessToken());
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, NewJwtCookie.toString())
        .build();
  }
}
