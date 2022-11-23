package httpApiConsumerIT.simpleApp;

import com.mca.client.annotation.enablement.EnableMcaClient;
import applications.simpleApp.SimpleApp;
import applications.simpleApp.ResultPayload;
import applications.simpleApp.StringClient;
import applications.simpleApp.StringController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@EnableMcaClient
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {SimpleApp.class, StringController.class})
public class HttpApiConsumerTest {

    @Autowired
    private StringClient stringClient;

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
