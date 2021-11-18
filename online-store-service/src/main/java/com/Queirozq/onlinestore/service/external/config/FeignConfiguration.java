package com.Queirozq.onlinestore.service.external.config;

import feign.Logger;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;

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
}
