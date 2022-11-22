# About

`springboot-mca-client` is a simple springboot http clients configurer. This library aims to facilitate the management of APIs consumptions at runtime.<br/>
Samples are available [here](https://github.com/microservices-communication-framework/samples)

## Installation
The API provider service should expose OpenAPI docs, for springboot applications please follow this [link](https://springdoc.org/).<br/>
The API consumer service should include the following dependency:

### Maven

```xml
<dependency>
    <groupId>io.github.ahmedriahi</groupId>
    <artifactId>springboot-mca-client</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

## Http client configuration
Create Http client using `@McaClient` and `@HttpApiConsumer` annotations

```java
@McaClient(serviceName = "simple-api-provider")
public interface SimpleProviderAPIClient {

    @HttpApiConsumer(method = PathItem.HttpMethod.GET, path = "/api/v1/string/lowercase/{value}")
    String lowercase(@PathVariable("value") String value);

    @HttpApiConsumer(method = PathItem.HttpMethod.POST, path = "/api/v1/calculation/{operation}")
    CalculationResultPayload calculationUsingPathVariable(@PathVariable("operation") String operation, @RequestBody CalculationPayload value);

    @HttpApiConsumer(method = PathItem.HttpMethod.POST, path = "/api/v1/calculation")
    CalculationResultPayload calculationUsingRequestParam(@RequestParam("operation") String operation, @RequestBody CalculationPayload value);
}
```

## Http service provider configuration 

Configure Http API provider using the following configuration, url attribute points to OpenAPI docs

```yaml
mca:
  providers:
    simple-api-provider:
      url: http://localhost:5050/v3/api-docs
```

