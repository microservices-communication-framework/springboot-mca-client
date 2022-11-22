package com.mca.client.annotation.apiConsumer.httpApiConsumer;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

public class Auth0AudienceExchangeFilterFunction implements ExchangeFilterFunction {

    private static final String CLIENT_REGISTRATION_ID_ATTR_NAME = OAuth2AuthorizedClient.class.getName()
            .concat(".CLIENT_REGISTRATION_ID");

    @Override
    public Mono<ClientResponse> filter(ClientRequest clientRequest, ExchangeFunction exchangeFunction) {
        String clientId = (String) clientRequest.attributes().get(CLIENT_REGISTRATION_ID_ATTR_NAME);

        clientRequest = ClientRequest.from(clientRequest)
                .body(((BodyInserters.FormInserter)clientRequest.body()).with("audience","http://localhost:5051/"))
                .build();
        return exchangeFunction.exchange(clientRequest);
    }
}
