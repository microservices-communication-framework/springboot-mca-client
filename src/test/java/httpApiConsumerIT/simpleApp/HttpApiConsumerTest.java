package httpApiConsumerIT.simpleApp;

import applications.simpleApp.ResultPayload;
import applications.simpleApp.StringClient;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {SimpleApp.class, StringController.class})
public class HttpApiConsumerTest {

    @Autowired
    private StringClient stringClient;

    public void testPathVariableEndpoint() {
        String result = this.stringClient.lowercasePathVariable("OKEY");
        assertThat(result).isEqualTo("okey");
    }

    public void testRequestParamEndpoint() {
        String result = this.stringClient.lowercaseRequestParam("OKEY");
        assertThat(result).isEqualTo("okey");
    }

    public void testRequestParamEndpointResultPayload() {
        ResultPayload result = this.stringClient.lowercaseRequestParamResultPayload("OKEY");
        assertThat(result.getResult()).isEqualTo("okey");
    }
}
