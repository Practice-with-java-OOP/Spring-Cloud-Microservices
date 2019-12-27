package com.syphan.practice.proxy.gateway.swaggerConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerProxyConfig {
    @Autowired
    ZuulProperties properties;

    @Primary
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider() {
        return () -> {
            List<SwaggerResource> resources = new ArrayList<>();

            /**
             * Show all
             */
//            properties.getRoutes().values()
//                    .forEach(zuulRoute -> resources.add(createResource(zuulRoute.getServiceId(), zuulRoute.getId(), "2.0")));

            /**
             * custom
             */
            resources.add(createResource("employee-service", "/employee/v2/api-docs", "2.0"));
            resources.add(createResource("department-service", "/department/v2/api-docs", "2.0"));
            resources.add(createResource("organization-service", "/organization/v2/api-docs", "2.0"));
            return resources;
        };
    }

    private SwaggerResource createResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
