# About

`springboot-mca-client` is a simple lightweight springboot http clients configurer. This library aims to facilitate the management of APIs consumptions.<br/>
This project in on it's very early stage, all kinds of contibutions/feedbacks are welcome.<br/><br/>
[![Java CI with Maven](https://github.com/microservices-communication-framework/springboot-mca-client/actions/workflows/maven.yml/badge.svg?branch=main)](https://github.com/microservices-communication-framework/springboot-mca-client/actions/workflows/maven.yml)


## Installation
The API provider service should expose OpenAPI docs, for springboot applications please follow this [link](https://springdoc.org/).<br/>
The API consumer service should include the following dependency:

### Maven

```xml
<dependency>
    <groupId>io.github.ahmedriahi</groupId>
    <artifactId>springboot-mca-client</artifactId>
    <version>0.0.1</version>
</dependency>
```

## Application configuration

Add both `@EnableMcaClient` and `@InterfaceComponentScan` to your main springboot application class

```java
@SpringBootApplication
@EnableMcaClient
@InterfaceComponentScan
public class ConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApp.class, args);
    }
}
```

### Http client configuration
Create Http client using `@McaClient` and `@HttpApiConsumer` annotations

```java
@McaClient(providerName = "simple-api-provider")
public interface SimpleProviderAPIClient {

    @HttpApiConsumer(method = PathItem.HttpMethod.GET, path = "/api/v1/string/lowercase/{value}")
    String lowercase(@PathVariable("value") String value);

    @HttpApiConsumer(method = PathItem.HttpMethod.POST, path = "/api/v1/calculation/{operation}")
    CalculationResultPayload calculationUsingPathVariable(@PathVariable("operation") String operation, @RequestBody CalculationPayload value);

    @HttpApiConsumer(method = PathItem.HttpMethod.POST, path = "/api/v1/calculation")
    CalculationResultPayload calculationUsingRequestParam(@RequestParam("operation") String operation, @RequestBody CalculationPayload value);
}
```

### Http service provider configuration 

Configure Http API provider using the following configuration, url attribute points to OpenAPI docs

```yaml
mca:
  providers:
    simple-api-provider:
      url: http://localhost:5050/v3/api-docs
```

### OAuth integration
Add OAuth configuration as follows, make sure that `mca.providers.provider_name` is referenced using `mca.spring.security.oauth2.client.registration.provider`

```yaml
mca:
  providers:
    simple-api-provider:
      url: http://localhost:5050/v3/api-docs
    secured-api-provider:
      url: http://localhost:5051/v3/api-docs

  spring:
    security:
      oauth2:
        client:
          registration:
            secured-api-provider:
              authorization-grant-type: client_credentials
              client-id: --
              client-secret: --
              audience: --
              provider: secured-api-provider
          provider:
            secured-api-provider:
              token-uri: --
```

Samples are available [here](https://github.com/microservices-communication-framework/samples).
