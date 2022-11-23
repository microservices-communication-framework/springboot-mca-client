package com.mca.client.annotation.enablement;


import org.springframework.stereotype.Service;
import org.thepavel.icomponent.Handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Handler("mcaClientMethodHandler")
@Service
public @interface McaClient {

    String providerName();
}
