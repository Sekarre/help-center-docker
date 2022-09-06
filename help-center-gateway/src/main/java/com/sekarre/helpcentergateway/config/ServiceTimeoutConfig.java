package com.sekarre.helpcentergateway.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ServiceTimeoutConfig {

    @Value("${chat.timeout:4}")
    private Long chatTimeout;

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> longResponseCustomizer() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.ofDefaults();
        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(chatTimeout))
                .build();

        return factory -> factory.configure(builder -> builder.circuitBreakerConfig(circuitBreakerConfig)
                .timeLimiterConfig(timeLimiterConfig).build(), "fallback-chat", "fallback-notification");
    }
}
