package com.ubap.bookslookup;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateFactory implements FactoryBean<RestTemplate> {

    @Override
    public RestTemplate getObject() throws Exception {
        return new RestTemplate();
    }

    @Override
    public Class<?> getObjectType() {
        return RestTemplate.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}