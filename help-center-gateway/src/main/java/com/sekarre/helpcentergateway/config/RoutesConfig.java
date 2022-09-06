package com.sekarre.helpcentergateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.gateway.filter.factory.DedupeResponseHeaderGatewayFilterFactory.Strategy.RETAIN_FIRST;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN;

@Component
public class RoutesConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("core", r -> r.path("/api/v1/issues/**", "/api/v1/comments/**", "/api/v1/users/**")
                        .filters(f-> f
                                .dedupeResponseHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, RETAIN_FIRST.name())
                                .dedupeResponseHeader(ACCESS_CONTROL_ALLOW_ORIGIN, RETAIN_FIRST.name())
                                .circuitBreaker(config -> config
                                        .setName("fallback-core")
                                        .setFallbackUri("forward:/fallback/default")))
                        .uri("lb://help-center-core"))
                .route("auth", r -> r.path("/api/v1/auth/**")
                        .filters(f-> f
                                .dedupeResponseHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, RETAIN_FIRST.name())
                                .dedupeResponseHeader(ACCESS_CONTROL_ALLOW_ORIGIN, RETAIN_FIRST.name())
                                .circuitBreaker(config -> config
                                        .setName("fallback-auth")
                                        .setFallbackUri("forward:/fallback/default")))
                        .uri("lb://help-center-auth"))
                .route("chat", r -> r.path("/api/v1/chat-info/**", "/websocket/**")
                        .filters(f-> f
                                .dedupeResponseHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, RETAIN_FIRST.name())
                                .dedupeResponseHeader(ACCESS_CONTROL_ALLOW_ORIGIN, RETAIN_FIRST.name())
                                .circuitBreaker(config -> config
                                        .setName("fallback-chat")
                                        .setFallbackUri("forward:/fallback/default")))
                        .uri("lb://help-center-chat"))
                .route("notification", r -> r.path("/api/v1/event-notifications/**")
                        .filters(f-> f
                                .dedupeResponseHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, RETAIN_FIRST.name())
                                .dedupeResponseHeader(ACCESS_CONTROL_ALLOW_ORIGIN, RETAIN_FIRST.name())
                                .circuitBreaker(config -> config
                                        .setName("fallback-notification")
                                        .setFallbackUri("forward:/fallback/default")))
                        .uri("lb://help-center-notification"))
                .route("notification-sse", r -> r.path("/api/v1/sse/**")
                        .filters(f-> f
                                .dedupeResponseHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, RETAIN_FIRST.name())
                                .dedupeResponseHeader(ACCESS_CONTROL_ALLOW_ORIGIN, RETAIN_FIRST.name()))
                        .uri("lb://help-center-notification"))
                .build();
    }
}