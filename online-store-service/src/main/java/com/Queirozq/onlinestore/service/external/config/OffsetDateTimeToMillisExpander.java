package com.Queirozq.onlinestore.service.external.config;

import feign.Param;

import java.time.OffsetDateTime;

public class OffsetDateTimeToMillisExpander implements Param.Expander{
    @Override
    public String expand(Object value) {
        //Se o objeto não for OffSetDateTime nem faz a conversão.
        if (!OffsetDateTime.class.isAssignableFrom(value.getClass())){
            throw new IllegalArgumentException("This expander is not supporting the type");
        }
        long millis = ((OffsetDateTime) value).toInstant().toEpochMilli();
        return "" + millis;
    }
}
