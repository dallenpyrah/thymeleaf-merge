package io.github.dallenpyrah.thymeleaf.merge.boot;

import io.github.dallenpyrah.thymeleaf.merge.ThMergeEngineConfigurer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.thymeleaf.spring6.SpringTemplateEngine;

@AutoConfiguration
@ConditionalOnClass(SpringTemplateEngine.class)
public class ThymeleafMergeAutoConfiguration {
    @Bean
    public static BeanPostProcessor thymeleafMergeEnginePostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) {
                if (bean instanceof SpringTemplateEngine) {
                    ThMergeEngineConfigurer.register((SpringTemplateEngine) bean);
                }
                return bean;
            }
        };
    }
}
