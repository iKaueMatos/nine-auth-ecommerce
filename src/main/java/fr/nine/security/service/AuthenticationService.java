package fr.nine.security.service;

import fr.nine.security.payload.request.AuthenticationRequest;
import fr.nine.security.payload.request.RegisterRequest;
import fr.nine.security.payload.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
