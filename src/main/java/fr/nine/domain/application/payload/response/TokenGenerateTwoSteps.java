package fr.nine.domain.application.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenGenerateTwoSteps {
  @JsonProperty("access_token")
  private String accessToken;
}
