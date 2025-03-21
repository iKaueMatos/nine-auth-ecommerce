package fr.nine.domain.application.usecase;

import org.springframework.stereotype.Component;

import fr.nine.domain.application.service.auth.IGenerateTokenRedefinePasswordService;
import fr.nine.infra.repository.UserRepository;
import fr.nine.infra.service.sendEmail.EmailService;
import fr.nine.infra.service.sendEmail.EmailStrategy;
import fr.nine.infra.service.sendEmail.SESEmailStrategy;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.ses.SesClient;

@Component
@RequiredArgsConstructor
public class GenerateTokenRedefinePasswordUseCase {
  private final UserRepository userRepository;
  protected final IGenerateTokenRedefinePasswordService generateTokenRedefinePasswordService;
  private final EmailService emailService;
  private final SesClient sesClient;

  public void execute(String email) {
    StringBuilder result = new StringBuilder();

    userRepository.findByEmail(email).ifPresentOrElse(user -> {
        String token = generateTokenRedefinePasswordService.initiatePasswordReset(user);

        String subject = "Redefinição de Senha";
        String htmlBody = "<h1>Olá " + user.getFirstname() + ",</h1>"
                + "<p>Para redefinir sua senha, utilize o seguinte token: <strong>" + token + "</strong>.</p>"
                + "<p>Insira este token na página de redefinição de senha.</p>";
        String textBody = "Olá " + user.getFirstname() + ",\n"
                + "Para redefinir sua senha, utilize o seguinte token: " + token + ".\n"
                + "Insira este token na página de redefinição de senha.";

        EmailStrategy sesStrategy = new SESEmailStrategy();
        emailService.setEmailStrategy(sesStrategy);
        emailService.sendEmail(email, subject, htmlBody, textBody);
        result.append("Email enviado com sucesso para ").append(email);
    }, () -> {
      result.append("Não foi possível enviar email para o endereço informado: ").append(email);
    });
  }
}