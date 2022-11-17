package com.mca.client.annotation.apiConsumer.httpApiConsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mca.client.annotation.mcaClient.McaClient;
import com.mca.client.openapi.OpenApiSpecsContext;
import com.mca.client.utils.ClassUtils;
import com.mca.openApi.OpenApiClient;
import io.swagger.oas.models.OpenAPI;
import io.swagger.oas.models.PathItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.thepavel.icomponent.handler.MethodHandler;
import org.thepavel.icomponent.metadata.MethodMetadata;
import org.thepavel.icomponent.metadata.ParameterMetadata;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class HttpApiConsumerMethodHandler implements MethodHandler {

    @Autowired
    private OpenApiSpecsContext openApiSpecsContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Object handle(Object[] arguments, MethodMetadata methodMetadata) {
        log.info("HttpApiConsumerMethodHandler");
        Map<String, Object> mcaClientParams = methodMetadata.getSourceClassMetadata().getAnnotationAttributes(McaClient.class.getCanonicalName());
        Map<String, Object> apiConsumerParams = methodMetadata.getAnnotationAttributes(HttpApiConsumer.class.getCanonicalName());

        Object requestBody = this.getRequestBody(arguments, methodMetadata);
        Map<String, Object> pathVariables = this.getPathVariables(arguments, methodMetadata);
        Map<String, Object> requestParamsVariables = this.getRequestParamsVariables(arguments, methodMetadata);

        OpenAPI openAPISpec = this.openApiSpecsContext.getSpec(mcaClientParams.get("serviceName").toString());
        OpenApiClient openApiClient = new OpenApiClient(openAPISpec);
        PathItem.HttpMethod httpMethod = PathItem.HttpMethod.valueOf(apiConsumerParams.get("method").toString());
        Object apiCallResult = openApiClient.call(httpMethod, apiConsumerParams.get("path").toString(), requestParamsVariables, pathVariables, requestBody);

        try {
            apiCallResult = this.objectMapper.readValue(apiCallResult.toString().getBytes(), Class.forName(methodMetadata.getReturnTypeMetadata().getResolvedType().getTypeName()));
        } catch (ClassNotFoundException | IOException e) {
            log.error(e.getMessage(), e);
        }
        return apiCallResult;
    }

    private Map<String, Object> getPathVariables(Object[] arguments, MethodMetadata methodMetadata) {
        List<ParameterMetadata> pathVariableParametersMetadata = methodMetadata.getParametersMetadata()
                .stream()
                .filter(parameterMetadata -> parameterMetadata.isAnnotated(PathVariable.class.getCanonicalName()))
                .collect(Collectors.toList());

        Map<String, Object> pathVariables = new HashMap<>();

        for (ParameterMetadata parameterMetadata : pathVariableParametersMetadata) {
            String pathVariableName = parameterMetadata.getAnnotationAttributes(PathVariable.class.getCanonicalName()).get("name").toString();
            Object pathVariableValue = arguments[parameterMetadata.getOrder()];
            pathVariables.put(pathVariableName, pathVariableValue);
        }
        return pathVariables;
    }

    private Map<String, Object> getRequestParamsVariables(Object[] arguments, MethodMetadata methodMetadata) {
        List<ParameterMetadata> requestParamsParametersMetadata = methodMetadata.getParametersMetadata()
                .stream()
                .filter(parameterMetadata -> parameterMetadata.isAnnotated(RequestParam.class.getCanonicalName()))
                .collect(Collectors.toList());

        Map<String, Object> requestParamVariables = new HashMap<>();

        for (ParameterMetadata parameterMetadata : requestParamsParametersMetadata) {
            String pathVariableName = parameterMetadata.getAnnotationAttributes(RequestParam.class.getCanonicalName()).get("name").toString();
            Object pathVariableValue = arguments[parameterMetadata.getOrder()];
            requestParamVariables.put(pathVariableName, pathVariableValue);
        }
        return requestParamVariables;
    }

    private Object getRequestBody(Object[] arguments, MethodMetadata methodMetadata) {
        Optional<ParameterMetadata> requestBodyParameterMetadata = methodMetadata.getParametersMetadata()
                .stream()
                .filter(parameterMetadata -> parameterMetadata.isAnnotated(RequestBody.class.getCanonicalName()))
                .findAny();

        Object requestBody = null;
        if (requestBodyParameterMetadata.isPresent()) {
            requestBody = arguments[requestBodyParameterMetadata.get().getOrder()];
        }
        return requestBody;
    }
}
