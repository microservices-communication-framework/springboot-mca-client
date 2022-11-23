package applications.oauthApp;

import com.mca.client.annotation.api.consumer.http.HttpApiConsumer;
import com.mca.client.annotation.enablement.McaClient;
import io.swagger.oas.models.PathItem;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@McaClient(providerName = "fake-server")
public interface StringClient {

    @HttpApiConsumer(method = PathItem.HttpMethod.GET, path = "/api/v1/string/lowercase/{string}")
    String lowercasePathVariable(@PathVariable("string") String string);

    @HttpApiConsumer(method = PathItem.HttpMethod.GET, path = "/api/v1/string/lowercase")
    String lowercaseRequestParam(@RequestParam("string") String string);

    @HttpApiConsumer(method = PathItem.HttpMethod.GET, path = "/api/v1/string/lowercaseResultPayload")
    ResultPayload lowercaseRequestParamResultPayload(@RequestParam("string") String string);


}
