package com.mca.client.config;

import com.mca.client.annotation.api.consumer.http.oauth.McaOAuth2ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.mca.openapi")
@EnableConfigurationProperties(McaOAuth2ClientProperties.class)
public class McaClientConfig {

}
