package fr.nine.domain.enterprise.user.service.auth.RefleshTokenServiceImpl;

import fr.nine.domain.application.exception.TokenException;
import fr.nine.domain.application.payload.request.RefreshTokenRequest;
import fr.nine.domain.application.payload.response.RefreshTokenResponse;
import fr.nine.domain.application.service.auth.IJwtService;
import fr.nine.domain.application.service.auth.IRefreshTokenService;
import fr.nine.domain.enterprise.user.entities.RefreshToken;
import fr.nine.domain.enterprise.user.entities.User;
import fr.nine.domain.enterprise.user.enums.TokenType;
import fr.nine.infra.repository.RefreshTokenRepository;
import fr.nine.infra.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.time.Instant;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements IRefreshTokenService {
  private final UserRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final IJwtService jwtService;

  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshExpiration;
  @Value("${application.security.jwt.refresh-token.cookie-name}")
  private String refreshTokenName;

  @Override
  public RefreshToken createRefreshToken(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    RefreshToken refreshToken = RefreshToken.builder()
        .revoked(false)
        .user(user)
        .token(Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes()))
        .expiryDate(Instant.now().plusMillis(refreshExpiration))
        .build();
    return refreshTokenRepository.save(refreshToken);
  }

  @Override
  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token == null) {
      log.error("Token is null");
      throw new TokenException(null, "Token is null");
    }
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new TokenException(token.getToken(),
          "Refresh token was expired. Please make a new authentication request");
    }
    return token;
  }

  @Override
  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  @Override
  public RefreshTokenResponse generateNewToken(RefreshTokenRequest request) {
    User user = refreshTokenRepository.findByToken(request.getRefreshToken())
        .map(this::verifyExpiration)
        .map(RefreshToken::getUser)
        .orElseThrow(() -> new TokenException(request.getRefreshToken(), "Refresh token does not exist"));

    String token = jwtService.generateToken(user);
    return RefreshTokenResponse.builder()
        .accessToken(token)
        .refreshToken(request.getRefreshToken())
        .tokenType(TokenType.BEARER.name())
        .build();
  }

  @Override
  public ResponseCookie generateRefreshTokenCookie(String token) {
    return ResponseCookie.from(refreshTokenName, token)
        .path("/")
        .maxAge(refreshExpiration / 1000)
        .httpOnly(true)
        .secure(true)
        .sameSite("Strict")
        .build();
  }

  @Override
  public String getRefreshTokenFromCookies(HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, refreshTokenName);
    if (cookie != null) {
      return cookie.getValue();
    } else {
      return "";
    }
  }

  @Override
  public void deleteByToken(String token) {
    refreshTokenRepository.findByToken(token).ifPresent(refreshTokenRepository::delete);
  }

  @Override
  public ResponseCookie getCleanRefreshTokenCookie() {
    return ResponseCookie.from(refreshTokenName, "")
        .path("/")
        .httpOnly(true)
        .maxAge(0)
        .build();
  }
}
