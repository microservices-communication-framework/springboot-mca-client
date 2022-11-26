package com.mca.client.annotation.enablement;

import com.mca.client.config.OpenApiContextConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.thepavel.icomponent.InterfaceComponentScan;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@InterfaceComponentScan
@ComponentScan("com.mca.client")
@Import(OpenApiContextConfig.class)
public @interface EnableMcaClient {
}
