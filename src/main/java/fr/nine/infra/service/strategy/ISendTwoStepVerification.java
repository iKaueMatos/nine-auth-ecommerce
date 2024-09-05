package fr.nine.infra.service.strategy;

public interface ISendTwoStepVerification {
  void sendVerification(String email, String message);
}
