package applications.simpleApp;

import com.mca.client.openapi.OpenApiSpecsContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class CustomConfig {


    @Bean
    public OpenApiSpecsContext openApiSpecsContext() {
        OpenApiSpecsContext openApiSpecsContext = new OpenApiSpecsContext();
        return openApiSpecsContext;
    }
}
