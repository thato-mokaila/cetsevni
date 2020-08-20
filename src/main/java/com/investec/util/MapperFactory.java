package com.investec.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Factory which return a lazy singleton instance of the mapper.
 */
public class MapperFactory {

    // initialization on demand
    public static class MapperHolder {
        public static ObjectMapper objectMapper = new ObjectMapper()
                // .registerModules(new SimpleModule().addDeserializer(Address.class, new AddressDeserializer()))
                .enable(SerializationFeature.INDENT_OUTPUT)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public static ObjectMapper getMapper() {
        return MapperHolder.objectMapper;
    }
}
