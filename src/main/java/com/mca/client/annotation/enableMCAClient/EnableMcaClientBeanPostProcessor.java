package com.mca.client.annotation.enableMCAClient;

import com.mca.client.McaClientBootstrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class EnableMcaClientBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private McaClientBootstrapper mcaClientBootstrapper;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(EnableMcaClient.class)) {
            this.mcaClientBootstrapper.bootstrap();
        }
        return bean;
    }
}
