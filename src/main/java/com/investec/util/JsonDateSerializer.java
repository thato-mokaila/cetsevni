package com.investec.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.investec.service.IAddress;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JsonDateSerializer extends JsonSerializer<LocalDateTime> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(IAddress.DATE_FORMAT);

    @Override
    public void serialize(LocalDateTime date, JsonGenerator generator, SerializerProvider sp)
            throws IOException, JsonProcessingException {
        final String dateString = date.format(this.formatter);
        generator.writeString(dateString);
    }
}