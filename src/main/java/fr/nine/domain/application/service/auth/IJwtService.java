package fr.nine.domain.application.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
  String extractUserName(String token);
  String generateToken(UserDetails userDetails);
  boolean isTokenValid(String token, UserDetails userDetails);
  ResponseCookie generateJwtCookie(String jwt);
  String getJwtFromCookies(HttpServletRequest request);
  ResponseCookie getCleanJwtCookie();
}
