package fr.nine.domain.application.service.auth;

import fr.nine.domain.application.payload.request.AuthenticationRequest;
import fr.nine.domain.application.payload.request.RegisterRequest;
import fr.nine.domain.application.payload.response.AuthenticationResponse;

public interface AuthenticationService {
  AuthenticationResponse register(RegisterRequest request);
  AuthenticationResponse authenticate(AuthenticationRequest request);
}
