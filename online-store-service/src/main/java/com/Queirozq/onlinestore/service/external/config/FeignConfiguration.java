package com.Queirozq.onlinestore.service.external.config;

import feign.AsyncFeign;
import feign.Feign;
import feign.Logger;
import feign.Target;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.ReflectionUtils;

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
    public Targeter feignTargeter(){
            return new Targeter() {
                @Override
                public <T> T target(FeignClientFactoryBean factory, Feign.Builder builder, FeignContext context, Target.HardCodedTarget<T> target) {
                    String contextId = factory.getContextId();
                    AsyncFeign.AsyncBuilder<Object> asyncBuilder = AsyncFeign.asyncBuilder();
                    asyncBuilder.decoder(context.getInstance(contextId, Decoder.class));
                    asyncBuilder.errorDecoder(context.getInstance(contextId, ErrorDecoder.class));
                    if(factory.isDecode404()){
                        asyncBuilder.decode404();
                    }
                    ReflectionUtils.doWithFields(AsyncFeign.AsyncBuilder.class, field -> {
                        ReflectionUtils.makeAccessible(field);
                        ReflectionUtils.setField(field, asyncBuilder, builder);
                    }, field -> field.getName().equalsIgnoreCase("builder"));
                    return asyncBuilder.target(target);
                }
            };
    }
}
