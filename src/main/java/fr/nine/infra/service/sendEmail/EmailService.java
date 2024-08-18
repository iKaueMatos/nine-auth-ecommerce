package fr.nine.infra.service.sendEmail;

import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
  private final SesClient sesClient;
  private final String senderEmail = "";

  public void sendResetPasswordEmail(String recipientEmail, String token) {
    try {
      SendTemplatedEmailRequest request = SendTemplatedEmailRequest.builder()
          .source(senderEmail)
          .destination(Destination.builder()
              .toAddresses(recipientEmail)
              .build())
          .template("PasswordResetTemplate")
          .templateData("{\"token\":\"" + token + "\"}")
          .build();

      sesClient.sendTemplatedEmail(request);
    } catch (SesException e) {
      e.printStackTrace();
    }
  }
}
