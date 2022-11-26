package com.mca.client.openapi;

import io.swagger.oas.models.OpenAPI;

import java.util.HashMap;
import java.util.Map;

public class OpenApiSpecsContext {

    private Map<String, OpenAPI> specsMap = new HashMap<>();

    public void addSpec(String serviceName, OpenAPI spec) {
        this.specsMap.put(serviceName, spec);
    }

    public OpenAPI getSpec(String name) {
        return this.specsMap.get(name);
    }
}
