package com.mca.client.config;

import com.mca.client.config.properties.McaClientProperties;
import com.mca.client.config.properties.ProviderProperties;
import com.mca.client.openapi.OpenApiSpecsContext;
import com.mca.openapi.OpenApiLoader;
import io.swagger.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class OpenApiContextConfig {

    @Autowired
    private McaClientProperties mcaClientProperties;

    @Autowired
    private OpenApiLoader openApiLoader;

    @Bean
    public OpenApiSpecsContext openApiSpecsContext() {
        OpenApiSpecsContext openApiSpecsContext = new OpenApiSpecsContext();
        for (Map.Entry<String, ProviderProperties> entry : this.mcaClientProperties.getProviders().entrySet()) {
            OpenAPI spec = this.openApiLoader.loadSpecFile(entry.getValue().getUrl());
            openApiSpecsContext.addSpec(entry.getKey(), spec);
        }
        return openApiSpecsContext;
    }
}
