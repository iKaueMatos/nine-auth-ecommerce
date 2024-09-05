package fr.nine.infra.service.sendEmail;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
  private EmailStrategy emailStrategy;

  public void setEmailStrategy(EmailStrategy emailStrategy) {
      this.emailStrategy = emailStrategy;
  }

  public void sendEmail(String recipientEmail, String subject, String htmlBody, String textBody) {
      emailStrategy.sendEmail(recipientEmail, subject, htmlBody, textBody);
  }
}
