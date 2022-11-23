package com.mca.client.annotation.api.consumer.http;

import com.mca.client.annotation.api.consumer.ApiConsumer;
import io.swagger.oas.models.PathItem;
import org.springframework.stereotype.Service;
import org.thepavel.icomponent.Handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ApiConsumer
@Handler("httpApiConsumerMethodHandler")
@Service
public @interface HttpApiConsumer {

    String path();
    PathItem.HttpMethod method();
}
