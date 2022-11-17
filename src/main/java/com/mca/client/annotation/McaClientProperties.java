package com.mca.client.annotation;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "mca")
public class McaClientProperties {

    private Map<String, String> providers;

}
