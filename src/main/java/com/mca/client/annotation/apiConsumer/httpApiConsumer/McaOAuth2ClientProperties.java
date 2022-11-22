package com.mca.client.annotation.apiConsumer.httpApiConsumer;

import lombok.Data;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "mca.spring.security.oauth2.client")
public class McaOAuth2ClientProperties {

    private final Map<String, OAuth2ClientProperties.Provider> provider = new HashMap();
    private Map<String, Registration> registration = new HashMap();

    @Data
    public static class Registration extends OAuth2ClientProperties.Registration {
        private String audience;
    }
}
