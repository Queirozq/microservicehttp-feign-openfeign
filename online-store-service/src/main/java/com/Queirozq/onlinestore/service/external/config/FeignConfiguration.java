package com.Queirozq.onlinestore.service.external.config;

import com.Queirozq.onlinestore.service.external.inventory.InventoryServiceClient;
import com.Queirozq.onlinestore.service.external.session.UserSessionClient;
import feign.*;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.micrometer.MicrometerCapability;
import feign.slf4j.Slf4jLogger;
import io.micrometer.core.instrument.Clock;
import io.micrometer.jmx.JmxConfig;
import io.micrometer.jmx.JmxMeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class FeignConfiguration {

    @Bean
    public UserSessionClient userSessionClient(){
        return AsyncFeign.asyncBuilder()
                .logLevel(Logger.Level.FULL)
                .logger(new Slf4jLogger())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .requestInterceptor(new SourceRequestInterceptor())
                .target(UserSessionClient.class, "http://localhost:8082");
    }

    @Bean
    public InventoryServiceClient inventoryServiceClient(){
        return Feign.builder()
                .addCapability(new MicrometerCapability(new JmxMeterRegistry(JmxConfig.DEFAULT, Clock.SYSTEM)))
                .logLevel(Logger.Level.FULL)
                .logger(new Slf4jLogger())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .errorDecoder(new InventoryServiceErrorDecoder())
                .retryer(new Retryer.Default(1000, 5000, 2))
                .requestInterceptor(new SourceRequestInterceptor())
                .target(InventoryServiceClient.class, "http://localhost:8081");
    }
}
