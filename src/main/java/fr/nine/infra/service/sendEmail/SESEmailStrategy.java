package fr.nine.infra.service.sendEmail;

import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SESEmailStrategy implements EmailStrategy {
    private final SesClient sesClient;
    
    @Value("${application.email.sender}")
    private final String senderEmail = "";

    @Override
    public void sendEmail(String recipientEmail, String subject, String htmlBody, String textBody) {
        try {
            SendEmailRequest request = SendEmailRequest.builder()
                .source(senderEmail)
                .destination(Destination.builder()
                    .toAddresses(recipientEmail)
                    .build())
                .message(Message.builder()
                    .subject(Content.builder()
                        .data(subject)
                        .build())
                    .body(Body.builder()
                        .html(Content.builder()
                            .data(htmlBody)
                            .build())
                        .text(Content.builder()
                            .data(textBody)
                            .build())
                        .build())
                    .build())
                .build();

            sesClient.sendEmail(request);
        } catch (SesException e) {
            e.printStackTrace();
        }
    }
}
