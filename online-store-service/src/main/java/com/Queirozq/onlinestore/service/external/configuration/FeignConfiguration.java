package com.Queirozq.onlinestore.service.external.configuration;

import com.Queirozq.onlinestore.service.external.session.UserSessionClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    public UserSessionClient userSessionClient(){
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(UserSessionClient.class, "http://localhost:8082");
    }
}
