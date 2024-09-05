package fr.nine.infra.controller.http.auth.twostepsverification;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(name = "/api/v1/auth")
public class TwoStepsVerificationController {
  @PostMapping("/twoSteps")
  public ResponseEntity<String> handle(@Valid @RequestParam String email) {
    
    return ResponseEntity.ok("Email enviado com sucesso!");
  }
}
