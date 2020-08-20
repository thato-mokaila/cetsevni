package com.investec.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.investec.domain.Address;
import com.investec.ext.BusinessAddress;
import com.investec.ext.PhysicalAddress;
import com.investec.ext.PostalAddress;

import java.io.IOException;

/**
 * Custom deserializer fro mapping json object to specific
 * sub-classes. This should derive the metadata from the type
 * field in the json object which seem to be consistent across
 * all address instances.
 */
public class AddressDeserializer extends StdDeserializer<Address> {

    public AddressDeserializer() {
        this(Address.class);
    }

    public AddressDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public Address deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        final ObjectMapper mapper = (ObjectMapper)jsonParser.getCodec();

        // We can also use the name property
        switch (node.get("type").get("code").textValue()) {
            case "1":
                return mapper.treeToValue(node, PhysicalAddress.class);
            case "2":
                return mapper.treeToValue(node, PostalAddress.class);
            case "3":
                return mapper.treeToValue(node, BusinessAddress.class);
        }
        return null;
    }
}
