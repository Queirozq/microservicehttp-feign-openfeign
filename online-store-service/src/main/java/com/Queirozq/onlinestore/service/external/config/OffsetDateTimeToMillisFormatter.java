package com.Queirozq.onlinestore.service.external.config;

import org.springframework.format.Formatter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Locale;

public class OffsetDateTimeToMillisFormatter implements Formatter<OffsetDateTime> {

    @Override
    public OffsetDateTime parse(String text, Locale locale) {
        long millis = Long.parseLong(text);
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneOffset.UTC);
    }

    @Override
    public String print(OffsetDateTime object, Locale locale) {
        return "" + object.toInstant().toEpochMilli();
    }
}