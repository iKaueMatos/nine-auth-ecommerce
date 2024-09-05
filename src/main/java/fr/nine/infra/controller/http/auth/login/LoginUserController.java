package fr.nine.infra.controller.http.auth.login;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.nine.domain.application.payload.request.AuthenticationRequest;
import fr.nine.domain.application.payload.response.AuthenticationResponse;
import fr.nine.domain.application.service.auth.IAuthenticationService;
import fr.nine.domain.application.service.auth.IJwtService;
import fr.nine.domain.application.service.auth.IRefreshTokenService;
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
  private final IJwtService jwtService;
  private final IAuthenticationService authenticationService;
  private final IRefreshTokenService refreshTokenService;

  @PostMapping("/authenticate")
  @Operation(responses = {
      @ApiResponse(description = "Success", responseCode = "200"),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = {
          @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json") })
  })
  public ResponseEntity<AuthenticationResponse> handle(@RequestBody AuthenticationRequest request) {
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
