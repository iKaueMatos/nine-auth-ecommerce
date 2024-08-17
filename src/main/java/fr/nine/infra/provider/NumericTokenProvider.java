package fr.nine.infra.provider;

import java.util.Random;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class NumericTokenProvider {
  private static final int TOKEN_LENGTH = 6;
    private static final int EXPIRATION_MINUTES = 10;

    public String generateNumericToken() {
        Random random = new Random();
        StringBuilder token = new StringBuilder();

        for (int i = 0; i < TOKEN_LENGTH; i++) {
            int digit = random.nextInt(10);
            token.append(digit);
        }

        return token.toString();
    }

    public LocalDateTime calculateExpiryDate() {
        return LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES);
    }

    public boolean isTokenExpired(LocalDateTime expiryDate) {
        return expiryDate.isBefore(LocalDateTime.now());
    }
}
