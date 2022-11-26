package com.mca.client.annotation.api.consumer.http.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.endpoint.WebClientReactiveClientCredentialsTokenResponseClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@ConditionalOnProperty("mca.spring.security.oauth2.client.enabled")
public class OAuthClientConfiguration {

    @Autowired
    private McaOAuth2ClientProperties oAuth2ClientProperties;

    @Bean(name = "OAuthWebClient")
    public WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations) {
        InMemoryReactiveOAuth2AuthorizedClientService clientService =
                new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrations);
        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrations, clientService);
        WebClientReactiveClientCredentialsTokenResponseClient webClientReactiveClientCredentialsTokenResponseClient =
                new WebClientReactiveClientCredentialsTokenResponseClient();
        webClientReactiveClientCredentialsTokenResponseClient.setWebClient(this.auth0WebClient());
        webClientReactiveClientCredentialsTokenResponseClient.addHeadersConverter(source -> {
            HttpHeaders headers = new HttpHeaders();
            // Workaround to be able to fetch client audience
            headers.add(
                    Auth0AudienceExchangeFilterFunction.CLIENT_REGISTRATION_ID_ATTR_NAME,
                    source.getClientRegistration().getRegistrationId()
            );
            return headers;
        });
        authorizedClientManager.setAuthorizedClientProvider(
                ReactiveOAuth2AuthorizedClientProviderBuilder
                        .builder()
                        .clientCredentials(
                            clientCredentialsGrantBuilder -> clientCredentialsGrantBuilder
                                .accessTokenResponseClient(webClientReactiveClientCredentialsTokenResponseClient)
                        ).build()
        );
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        return WebClient.builder()
                .filter(oauth)
                .build();
    }

    private WebClient auth0WebClient() {
        Auth0AudienceExchangeFilterFunction audienceExchangeFilterFunction =
                new Auth0AudienceExchangeFilterFunction(this.oAuth2ClientProperties);
        return WebClient.builder()
                .filter(audienceExchangeFilterFunction)
                .build();
    }

    @Bean
    public ReactiveClientRegistrationRepository clientRegistrations() {
        List<ClientRegistration> clientRegistrations = new ArrayList<>();
        for (Map.Entry<String, McaOAuth2ClientProperties.Registration> registrationEntry :
                this.oAuth2ClientProperties.getRegistration().entrySet()) {
            clientRegistrations.add(ClientRegistration
                    .withRegistrationId(registrationEntry.getKey())
                    .tokenUri(this.oAuth2ClientProperties.getProvider()
                            .get(registrationEntry.getValue().getProvider())
                            .getTokenUri()
                    )
                    .clientId(registrationEntry.getValue().getClientId())
                    .clientSecret(registrationEntry.getValue().getClientSecret())
                    .scope(registrationEntry.getValue().getScope())
                    .authorizationGrantType(
                            new AuthorizationGrantType(registrationEntry.getValue().getAuthorizationGrantType())
                    )
                    .build());
        }
        return new InMemoryReactiveClientRegistrationRepository(clientRegistrations);
    }
}
