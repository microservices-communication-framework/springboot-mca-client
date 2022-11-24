package com.mca.client.annotation.enablement;

import com.mca.client.config.properties.McaClientProperties;
import com.mca.client.config.properties.ProviderProperties;
import com.mca.client.openapi.OpenApiSpecsContext;
import com.mca.openapi.OpenApiLoader;
import io.swagger.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class McaClientBootstrapper {

    @Autowired
    private McaClientProperties mcaClientProperties;

    @Autowired
    private OpenApiLoader openApiLoader;

    @Autowired
    private OpenApiSpecsContext openApiSpecsContext;

    public void bootstrap() {
        for (Map.Entry<String, ProviderProperties> entry : this.mcaClientProperties.getProviders().entrySet()) {
            OpenAPI spec = this.openApiLoader.loadSpecFile(entry.getValue().getUrl());
            this.openApiSpecsContext.addSpec(entry.getKey(), spec);
        }
    }

}
