package com.open.api.annotation;

import com.open.api.service.OpenApiService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class OpenApiAnnotationFactory {

    private final ApplicationContext applicationContext;
    private final Map<String, OpenApiService> openApiServiceMap = new HashMap<>();

    public OpenApiAnnotationFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(OpenApiAnnotation.class);

        beans.values().forEach(bean -> {
            OpenApiAnnotation openApiAnnotation = bean.getClass().getAnnotation(OpenApiAnnotation.class);
            String beanName = openApiAnnotation.value();

            if(openApiServiceMap.containsKey(beanName)) {
                throw new IllegalArgumentException(beanName + " is already existed.");
            }

            openApiServiceMap.put(beanName, (OpenApiService) bean);
        });
    }

    public OpenApiService getOpenApiService(String serviceName) {
        return openApiServiceMap.get(serviceName);
    }
}
