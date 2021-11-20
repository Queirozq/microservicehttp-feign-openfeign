package com.Queirozq.onlinestore.service.external.config;

import feign.Feign;
import feign.Logger;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.openfeign.CircuitBreakerNameResolver;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;

import java.time.Duration;

@Configuration
@EnableFeignClients(basePackages = "com.Queirozq.onlinestore.service.external")
public class FeignConfiguration implements FeignFormatterRegistrar {
    @Override
    public void registerFormatters(FormatterRegistry registry) {
        registry.addFormatter(new OffsetDateTimeToMillisFormatter());
    }

    @Bean
    public Logger.Level loggerLevel(){
        return Logger.Level.FULL;
    }

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> circuitBreakerFactoryCustomizer(){
        CircuitBreakerConfig cbConfig = CircuitBreakerConfig.custom()
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(5)
                .failureRateThreshold(20.0f)
                .waitDurationInOpenState(Duration.ofSeconds(5))
                .permittedNumberOfCallsInHalfOpenState(5)
                .build();
        return resilience4JCircuitBreakerFactory -> resilience4JCircuitBreakerFactory.configure(builder ->
                builder.circuitBreakerConfig(cbConfig), "UserSessionClient#validateSession(UUID)","UserSessionClient#validateSession(Map,Map)");

    }

    @Bean
    public CircuitBreakerNameResolver circuitBreakerNameResolver(){
        return (feignClientName, target, method) -> Feign.configKey(target.type(), method);
    }
}
