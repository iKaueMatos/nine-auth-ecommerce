package fr.nine.infra.service.sendEmail;

public interface EmailStrategy {
  void sendEmail(String recipientEmail, String subject, String htmlBody, String textBody);
}
