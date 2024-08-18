package fr.nine.domain.enterprise.user.service.auth.OAuth2User;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import fr.nine.domain.enterprise.user.entities.User;
import fr.nine.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {
  private final UserRepository userRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);
    String provider = userRequest.getClientRegistration().getRegistrationId();
    String providerId = oAuth2User.getName();
    String email = oAuth2User.getAttribute("email");
    String name = oAuth2User.getAttribute("name");

    User user = userRepository.findByProviderAndProviderId(provider, providerId)
        .orElseGet(() -> new User());

    user.setEmail(email);
    user.setFirstname(name);
    user.setProvider(provider);
    user.setProviderId(providerId);
    user.setAccessToken(userRequest.getAccessToken().getTokenValue());
    if (userRequest.getAdditionalParameters().containsKey("refresh_token")) {
      String refreshToken = (String) userRequest.getAdditionalParameters().get("refresh_token");
      user.setRefreshToken(refreshToken);
    }

    userRepository.save(user);
    return oAuth2User;
  }
}
