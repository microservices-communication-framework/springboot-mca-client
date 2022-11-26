package httpApiConsumerIT.simpleApp;

import applications.simpleApp.*;
import com.mca.client.config.properties.McaClientProperties;
import com.mca.client.config.properties.ProviderProperties;
import com.mca.client.openapi.OpenApiSpecsContext;
import com.mca.openapi.OpenApiLoader;
import io.swagger.oas.models.OpenAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {SimpleApp.class, StringController.class})
@ContextConfiguration(classes = {CustomConfig.class, SimpleApp.class})
public class HttpApiConsumerTest {

    @Autowired
    private StringClient stringClient;

    @Autowired
    private McaClientProperties mcaClientProperties;

    @Autowired
    private OpenApiLoader openApiLoader;


    @Autowired
    private OpenApiSpecsContext openApiSpecsContext;

    @BeforeEach
    public void beforeEach() {
        for (Map.Entry<String, ProviderProperties> entry : this.mcaClientProperties.getProviders().entrySet()) {
            OpenAPI spec = this.openApiLoader.loadSpecFile(entry.getValue().getUrl());
            this.openApiSpecsContext.addSpec(entry.getKey(), spec);
        }
    }

    @Test
    public void testPathVariableEndpoint() {
        String result = this.stringClient.lowercasePathVariable("OKEY");
        assertThat(result).isEqualTo("okey");
    }

    @Test
    public void testRequestParamEndpoint() {
        String result = this.stringClient.lowercaseRequestParam("OKEY");
        assertThat(result).isEqualTo("okey");
    }

    @Test
    public void testRequestParamEndpointResultPayload() {
        ResultPayload result = this.stringClient.lowercaseRequestParamResultPayload("OKEY");
        assertThat(result.getResult()).isEqualTo("okey");
    }
}
