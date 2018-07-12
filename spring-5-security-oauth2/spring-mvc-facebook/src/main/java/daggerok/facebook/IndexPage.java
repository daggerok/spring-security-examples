package daggerok.facebook;

import io.vavr.collection.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class IndexPage {

  final OAuth2AuthorizedClientService authorizedClientService;

  @ResponseBody
  @GetMapping("/api/v1/json-data")
  public Map map(final OAuth2AuthenticationToken authentication) {

    final OAuth2AuthorizedClient authorizedClient =
        authorizedClientService.loadAuthorizedClient(
            authentication.getAuthorizedClientRegistrationId(),
            authentication.getName());

    return HashMap.of(
        "authorizedClient", HashMap.of(
            "accessToken", HashMap.of(
                "issuedAt", authorizedClient.getAccessToken().getIssuedAt(),
                "expiresAt", authorizedClient.getAccessToken().getExpiresAt(),
                "tokenType", authorizedClient.getAccessToken().getTokenType(),
                "tokenValue", authorizedClient.getAccessToken().getTokenValue(),
                "scopes", authorizedClient.getAccessToken().getScopes()
            ),
            "clientRegistration", HashMap.of(
                "scopes", authorizedClient.getClientRegistration().getScopes(),
                "registrationId", authorizedClient.getClientRegistration().getRegistrationId(),
                "redirectUriTemplate", authorizedClient.getClientRegistration().getRedirectUriTemplate(),
                "providerDetails", authorizedClient.getClientRegistration().getProviderDetails(),
                "clientSecret", authorizedClient.getClientRegistration().getClientSecret(),
                "clientName", authorizedClient.getClientRegistration().getClientName(),
                "clientId", authorizedClient.getClientRegistration().getClientId(),
                "clientAuthenticationMethod", authorizedClient.getClientRegistration().getClientAuthenticationMethod(),
                "authorizationGrantType", authorizedClient.getClientRegistration().getAuthorizationGrantType()
            ),
            "principalName", authorizedClient.getPrincipalName()
        ).toJavaMap(),
        "authentication", HashMap.of(
            "name", authentication.getName(),
            "details", authentication.getDetails(),
            "authorizedClientRegistrationId", authentication.getAuthorizedClientRegistrationId(),
            "authorities", authentication.getAuthorities(),
            "credentials", authentication.getCredentials(),
            "principal", HashMap.of(
                "attributes", authentication.getPrincipal().getAttributes(),
                "authorities", authentication.getPrincipal().getAuthorities()
            ).toJavaMap()
        ).toJavaMap()
    ).toJavaMap();
  }

  @GetMapping("/")
  public String index(Model model, OAuth2AuthenticationToken authentication) {
    OAuth2AuthorizedClient authorizedClient =
        this.authorizedClientService.loadAuthorizedClient(
            authentication.getAuthorizedClientRegistrationId(),
            authentication.getName());
    model.addAttribute("userName", authentication.getName());
    model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());
    return "index";
  }

  @GetMapping("/userinfo")
  public String userinfo(Model model, OAuth2AuthenticationToken authentication) {
    OAuth2AuthorizedClient authorizedClient =
        this.authorizedClientService.loadAuthorizedClient(
            authentication.getAuthorizedClientRegistrationId(),
            authentication.getName());
    Map userAttributes = Collections.emptyMap();
    String userInfoEndpointUri = authorizedClient.getClientRegistration()
                                                 .getProviderDetails().getUserInfoEndpoint().getUri();
    if (!StringUtils.isEmpty(userInfoEndpointUri)) {  // userInfoEndpointUri is optional for OIDC Clients
      userAttributes = WebClient.builder()
                                .filter(oauth2Credentials(authorizedClient))
                                .build()
                                .get()
                                .uri(userInfoEndpointUri)
                                .retrieve()
                                .bodyToMono(Map.class)
                                .block();
    }
    model.addAttribute("userAttributes", userAttributes);
    return "userinfo";
  }

  private ExchangeFilterFunction oauth2Credentials(OAuth2AuthorizedClient authorizedClient) {
    return ExchangeFilterFunction.ofRequestProcessor(
        clientRequest -> {
          ClientRequest authorizedRequest = ClientRequest.from(clientRequest)
                                                         .header(HttpHeaders.AUTHORIZATION,
                                                                 "Bearer " + authorizedClient.getAccessToken()
                                                                                             .getTokenValue())
                                                         .build();
          return Mono.just(authorizedRequest);
        });
  }
}
