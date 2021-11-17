package com.Queirozq.onlinestore.service.external.config;

import com.Queirozq.onlinestore.service.external.inventory.InventoryServiceClient;
import com.Queirozq.onlinestore.service.external.session.UserSessionClient;
import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class FeignConfiguration {

    @Bean
    public UserSessionClient userSessionClient(){
        return Feign.builder()
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
                .logLevel(Logger.Level.FULL)
                .logger(new Slf4jLogger())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .errorDecoder(new InventoryServiceErrorDecoder())
                .requestInterceptor(new SourceRequestInterceptor())
                .options(new Request.Options(10, TimeUnit.SECONDS, 10, TimeUnit.SECONDS, true))
                .target(InventoryServiceClient.class, "http://localhost:8081");
    }
}
