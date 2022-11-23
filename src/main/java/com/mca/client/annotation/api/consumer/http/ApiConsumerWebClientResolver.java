package com.mca.client.annotation.api.consumer.http;

import com.mca.client.annotation.api.consumer.http.oauth.McaOAuth2ClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ApiConsumerWebClientResolver {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private McaOAuth2ClientProperties mcaOAuth2ClientProperties;

    public WebClient getWebClient(String providerName) {
        boolean oauthProvider = this.mcaOAuth2ClientProperties.getProvider().containsKey(providerName);
        if (oauthProvider) {
            return this.applicationContext.getBean("OAuthWebClient", WebClient.class);
        } else {
            return WebClient.builder().build();
        }
    }
}
