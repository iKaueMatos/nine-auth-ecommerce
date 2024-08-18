package fr.nine.domain.enterprise.user.service.auth.AutheticationServiceImpl;

import fr.nine.domain.application.payload.request.AuthenticationRequest;
import fr.nine.domain.application.payload.request.RegisterRequest;
import fr.nine.domain.application.payload.response.AuthenticationResponse;
import fr.nine.domain.application.service.auth.AuthenticationService;
import fr.nine.domain.application.service.auth.JwtService;
import fr.nine.domain.application.service.auth.RefreshTokenService;
import fr.nine.domain.enterprise.user.entities.User;
import fr.nine.domain.enterprise.user.enums.TokenType;
import fr.nine.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final RefreshTokenService refreshTokenService;

  @Override
  public AuthenticationResponse register(RegisterRequest request) {
    var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
        .build();
    user = userRepository.save(user);
    var jwt = jwtService.generateToken(user);
    var refreshToken = refreshTokenService.createRefreshToken(user.getId());

    var roles = user.getRole().getAuthorities()
        .stream()
        .map(SimpleGrantedAuthority::getAuthority)
        .toList();

    return AuthenticationResponse.builder()
        .accessToken(jwt)
        .email(user.getEmail())
        .id(user.getId())
        .refreshToken(refreshToken.getToken())
        .roles(roles)
        .tokenType(TokenType.BEARER.name())
        .build();
  }

  @Override
  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    var user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
    var roles = user.getRole().getAuthorities()
        .stream()
        .map(SimpleGrantedAuthority::getAuthority)
        .toList();
    var jwt = jwtService.generateToken(user);
    var refreshToken = refreshTokenService.createRefreshToken(user.getId());
    return AuthenticationResponse.builder()
        .accessToken(jwt)
        .roles(roles)
        .email(user.getEmail())
        .id(user.getId())
        .refreshToken(refreshToken.getToken())
        .tokenType(TokenType.BEARER.name())
        .build();
  }
}
