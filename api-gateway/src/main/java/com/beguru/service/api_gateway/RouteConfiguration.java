package com.beguru.service.api_gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route( "web-products",
                        r -> r.path("/web/products/**")
                                .filters(f ->
                                        f.rewritePath("/web/?(?<segment>.*)", "/v1/$\\{segment}")

                                )

                                .uri("lb://product-service")

                )
                .build();
    }

    /*
     *   cloud:
    gateway:
      routes:
      - id: rewritepath_route
        uri: http://product-service:8080
        predicates:
#        - Path=/mobile/products/**
        - Path=/web/products/**
        filters:
        - RewritePath=/web/?(?<segment>.*), /v1/$\{segment}
     */
}
