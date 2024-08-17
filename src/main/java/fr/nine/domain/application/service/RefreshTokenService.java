package fr.nine.domain.application.service;

import fr.nine.domain.application.payload.request.RefreshTokenRequest;
import fr.nine.domain.application.payload.response.RefreshTokenResponse;
import fr.nine.domain.enterprise.user.entities.RefreshToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

import java.util.Optional;

public interface RefreshTokenService {
  RefreshToken createRefreshToken(Long userId);
  RefreshToken verifyExpiration(RefreshToken token);
  Optional<RefreshToken> findByToken(String token);
  RefreshTokenResponse generateNewToken(RefreshTokenRequest request);
  ResponseCookie generateRefreshTokenCookie(String token);
  String getRefreshTokenFromCookies(HttpServletRequest request);
  void deleteByToken(String token);
  ResponseCookie getCleanRefreshTokenCookie();
}
