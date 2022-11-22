package com.mca.client.annotation.apiConsumer.httpApiConsumer;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

public class Auth0AudienceExchangeFilterFunction implements ExchangeFilterFunction {

    public static final String CLIENT_REGISTRATION_ID_ATTR_NAME = "REGISTRATION_ID";

    private McaOAuth2ClientProperties oAuth2ClientProperties;

    public Auth0AudienceExchangeFilterFunction(McaOAuth2ClientProperties oAuth2ClientProperties) {
        this.oAuth2ClientProperties = oAuth2ClientProperties;
    }

    @Override
    public Mono<ClientResponse> filter(ClientRequest clientRequest, ExchangeFunction exchangeFunction) {
        // temporary workaround
        String registrationId = clientRequest.headers().get(CLIENT_REGISTRATION_ID_ATTR_NAME).get(0);
        String audience = this.oAuth2ClientProperties.getRegistration().get(registrationId).getAudience();

        clientRequest = ClientRequest.from(clientRequest)
                .body(((BodyInserters.FormInserter) clientRequest.body()).with("audience", audience))
                .build();
        return exchangeFunction.exchange(clientRequest);
    }
}
