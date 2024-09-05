package fr.nine.domain.application.usecase;

import org.springframework.stereotype.Component;

import fr.nine.domain.enterprise.user.entities.User;
import fr.nine.infra.provider.generateNumericToken.NumericTokenProvider;
import fr.nine.infra.repository.UserRepository;
import fr.nine.infra.service.sendEmail.EmailService;
import fr.nine.infra.service.sendEmail.EmailStrategy;
import fr.nine.infra.service.sendEmail.SESEmailStrategy;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.ses.SesClient;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TwoStepsVerificationUseCase {
  private final NumericTokenProvider tokenProvider;
  private final UserRepository userRepository;
  private final EmailService emailService;
  private final SesClient sesClient;

  private String execute(String email) {
    StringBuilder result = new StringBuilder();

    userRepository.findByEmail(email).ifPresentOrElse(user -> {
        String token = tokenProvider.generateNumericToken();

        String subject = "Verificação de Dois Fatores";
        String htmlBody = "<h1>Olá " + user.getFirstname() + ",</h1>"
                + "<p>Seu código de verificação é: <strong>" + token + "</strong>.</p>"
                + "<p>Por favor, use este código para completar sua verificação.</p>";
        String textBody = "Olá " + user.getFirstname() + ",\n"
                + "Seu código de verificação é: " + token + ".\n"
                + "Por favor, use este código para completar sua verificação.";

        EmailStrategy sesStrategy = new SESEmailStrategy(sesClient, "sender@example.com");
        emailService.setEmailStrategy(sesStrategy);
        emailService.sendEmail(email, subject, htmlBody, textBody);
        result.append("Email enviado com sucesso para ").append(email);
    }, () -> {
        result.append("Não foi possível enviar email para o endereço informado: ").append(email);
    });

    return result.toString();
  }
}
