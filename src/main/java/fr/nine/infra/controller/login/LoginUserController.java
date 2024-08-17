package fr.nine.infra.controller.login;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.nine.domain.application.payload.request.AuthenticationRequest;
import fr.nine.domain.application.payload.response.AuthenticationResponse;
import fr.nine.domain.application.service.AuthenticationService;
import fr.nine.domain.application.service.JwtService;
import fr.nine.domain.application.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;

@RestController
@SecurityRequirements()
@RequiredArgsConstructor
@RequestMapping(name = "/api/v1/auth")
public class LoginUserController {
  private final JwtService jwtService;
  private final AuthenticationService authenticationService;
  private final RefreshTokenService refreshTokenService;

  @PostMapping("/authenticate")
  @Operation(responses = {
      @ApiResponse(description = "Success", responseCode = "200"),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = {
          @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json") })
  })
  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
    AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
    ResponseCookie jwtCookie = jwtService.generateJwtCookie(authenticationResponse.getAccessToken());
    ResponseCookie refreshTokenCookie = refreshTokenService
        .generateRefreshTokenCookie(authenticationResponse.getRefreshToken());
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
        .body(authenticationResponse);
  }
}
