package com.syphan.practice.proxy.gateway.swaggerConfig;

import org.hibernate.validator.internal.util.CollectionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
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
            resources.add(createResource("employee-service", "/employees/v2/api-docs", "2.0"));
            resources.add(createResource("department-service", "/departments/v2/api-docs", "2.0"));
            resources.add(createResource("organization-service", "/organizations/v2/api-docs", "2.0"));
            resources.add(createResource("auth-service", "/uaa/v2/api-docs", "2.0"));
            resources.add(createResource("proxy-service", "/v2/api-docs", "2.0"));
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

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.syphan.practice.proxy.gateway.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo())
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Arrays.asList(securitySchema(), apiKey()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Gateway Service")
                .description("Gateway with spring boot and cloud zuul")
                .contact(new Contact("Sy Phan", "http://phantiensy195@gmail.com", "phantiensy195@gmail.com"))
                .version("1.0.1")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("BearerToken", "Authorization", ApiKeyVehicle.HEADER.getValue());
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("^.*$"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> securityReferences = new ArrayList<>();
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
        authorizationScopes[0] = new AuthorizationScope("read", "read all");
        authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
        authorizationScopes[2] = new AuthorizationScope("write", "write all");
        securityReferences.add(new SecurityReference("oauth2", authorizationScopes));
        securityReferences.add(new SecurityReference("BearerToken", new AuthorizationScope[]{
                new AuthorizationScope("global", "accessEverything")
        }));
        return securityReferences;
    }

    @Bean
    public SecurityScheme apiCookieKey() {
        return new ApiKey(HttpHeaders.COOKIE, "apiKey", "cookie");
    }

    private OAuth securitySchema() {
        List<AuthorizationScope> authorizationScopeList = CollectionHelper.newArrayList();
        authorizationScopeList.add(new AuthorizationScope("read", "read all"));
        authorizationScopeList.add(new AuthorizationScope("trust", "trust all"));
        authorizationScopeList.add(new AuthorizationScope("write", "access all"));

        List<GrantType> grantTypes = CollectionHelper.newArrayList();
        GrantType passwordCredentialsGrant
                = new ResourceOwnerPasswordCredentialsGrant("http://localhost:8060/gw-created-by-syphan/uaa/oauth/token");
        grantTypes.add(passwordCredentialsGrant);
        return new OAuth("oauth2", authorizationScopeList, grantTypes);
    }
}
