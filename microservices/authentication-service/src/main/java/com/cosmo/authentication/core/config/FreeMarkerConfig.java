package com.cosmo.authentication.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
public class FreeMarkerConfig {

    @Bean
    public freemarker.template.Configuration freeMarkerConfiguration() {
        FreeMarkerConfigurationFactoryBean factoryBean = new FreeMarkerConfigurationFactoryBean();
        // Set your FreeMarker properties here
        factoryBean.setDefaultEncoding("UTF-8");
        try {
            factoryBean.afterPropertiesSet();
            return factoryBean.getObject();
        } catch (Exception e) {
            throw new RuntimeException("Error configuring FreeMarker", e);
        }
    }
}
