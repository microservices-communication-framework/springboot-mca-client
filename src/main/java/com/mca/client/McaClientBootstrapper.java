package com.mca.client;

import com.mca.client.properties.McaClientProperties;
import com.mca.client.properties.ProviderProperties;
import com.mca.openApi.OpenApiLoader;
import com.mca.client.openapi.OpenApiSpecsContext;
import io.swagger.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class McaClientBootstrapper {

    @Autowired
    private McaClientProperties mcaClientProperties;

    @Autowired
    private OpenApiLoader openAPILoader;

    @Autowired
    private OpenApiSpecsContext openAPISpecsContext;

    public void bootstrap() {
        for (Map.Entry<String, ProviderProperties> entry : this.mcaClientProperties.getProviders().entrySet()) {
            OpenAPI spec = this.openAPILoader.loadSpecFile(entry.getValue().getUrl());
            this.openAPISpecsContext.addSpec(entry.getKey(), spec);
        }
    }

}
