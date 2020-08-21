package com.investec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.investec.models.Address;
import com.investec.models.AddressLineDetail;
import com.investec.models.SimpleType;
import com.investec.models.SuburbOrDistrict;
import com.investec.models.BusinessAddress;
import com.investec.models.PhysicalAddress;
import com.investec.models.PostalAddress;
import com.investec.rules.Rule;
import com.investec.rules.StandardValidator;
import com.investec.rules.ZAValidator;
import com.investec.util.MapperFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class AppTest {

    private static final String PATH = "src/main/resources/";
    private static final String PHYSICAL_ADDRESS_PATH = PATH.concat("addresses-physical.json");
    private static final String BUSINESS_ADDRESS_PATH = PATH.concat("addresses-business.json");
    private static final String POSTAL_ADDRESS_PATH = PATH.concat("addresses-postal.json");
    private static final String ALL_ADDRESSES_PATH = PATH.concat("addresses.json");
    private static ObjectMapper MAPPER;
    private App app;
    private Address physicalAddress;

    @Before
    public void setUp() {
        MAPPER = MapperFactory.getMapper();
        app = new App();
        physicalAddress = new Address(
                "1",
                SimpleType.builder().code("1").name("Physical Address").build(),
                AddressLineDetail.builder().line1("123 Mangosuthu Street").line2("Unit 34").build(),
                "0921",
                "Pretoria",
                SimpleType.builder().code("ZA").name("South Africa").build(),
                SimpleType.builder().code("3").name("Gauteng").build(),
                SuburbOrDistrict.builder().code("Centurion").build(),
                LocalDateTime.now()
        );
    }

    @After
    public void tearDown() {
        MAPPER = null;
    }

    @Test
    public void testWeCanReadJsonAddresses() throws BusinessException {
        List<Address> addresses = app.loadAddresses(ALL_ADDRESSES_PATH);
        assertEquals(3, addresses.size());
    }

    @Test(expected = BusinessException.class)
    public void testExceptionHandlingForFailedRead() throws BusinessException {
        List<Address> addresses = app.loadAddresses("some-none-existing-path");
        assertEquals(3, addresses.size());
    }

    @Test
    public void testFormattedPrinting() {
        String s = app.formattedPrinting(physicalAddress);
        assertEquals(s, physicalAddress.formattedAddress());
    }

    @Test
    public void testWeCanPrettyPrintAddress() throws BusinessException, JsonProcessingException {
        String s = app.prettyPrintAddress(physicalAddress);
        assertEquals(s, MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(physicalAddress));
    }

    @Test
    public void testWeCanAddAdditionalRules() {
        assertEquals(4, new ZAValidator(Collections.singletonList(new Rule<>(null,""))).getRuleCount());
        assertEquals(3, new StandardValidator().getRuleCount());
    }

    @Test
    public void testWeCanValidateAddressList() {
        Map<Address, List<Rule<Address>>> validationResults = app.validateAddresses(
                Collections.singletonList(physicalAddress),
                Collections.singletonList(new StandardValidator())
        );

        // Single address returned from results map
        assertEquals(1, validationResults.size());
        // Single address passed all rule tests
        assertEquals(0, validationResults.get(physicalAddress).size());
    }

    @Test
    public void shouldSerializePhysicalAddress() throws IOException {

        Address physicalAddress = new Address(
                "1",
                SimpleType.builder().code("1").name("Physical Address").build(),
                AddressLineDetail.builder().line1("123 Mangosuthu Street").line2("Unit 34").build(),
                "0921",
                "Pretoria",
                SimpleType.builder().code("ZA").name("South Africa").build(),
                SimpleType.builder().code("3").name("Gauteng").build(),
                SuburbOrDistrict.builder().code("Centurion").build(),
                LocalDateTime.now()
        );

        String s = MAPPER.writeValueAsString(physicalAddress);
        assertNotNull(s);
        assertTrue(s.contains("Physical Address"));
        assertTrue(s.contains("Gauteng"));
        assertTrue(s.contains("Mangosuthu Street"));
    }

    @Test
    public void shouldDeSerializePhysicalAddress() throws IOException {

        byte[] jsonData = Files.readAllBytes(Paths.get(PHYSICAL_ADDRESS_PATH));
        final Address physicalAddress = MAPPER.readValue(jsonData, PhysicalAddress.class);
        // System.out.println(physicalAddress);
        assertEquals("1", physicalAddress.getType().getCode());
    }

    @Test
    public void shouldDeSerializePostalAddress() throws IOException {

        byte[] jsonData = Files.readAllBytes(Paths.get(POSTAL_ADDRESS_PATH));
        final Address businessAddress = MAPPER.readValue(jsonData, PostalAddress.class);
        assertEquals("2", businessAddress.getType().getCode());
    }

    @Test
    public void shouldDeSerializeBusinessAddress() throws IOException {

        byte[] jsonData = Files.readAllBytes(Paths.get(BUSINESS_ADDRESS_PATH));
        final Address businessAddress = MAPPER.readValue(jsonData, BusinessAddress.class);
        assertEquals("5", businessAddress.getType().getCode());
    }

    @Test
    public void shouldDeSerializeListOfAddress() throws IOException {

        byte[] jsonData = Files.readAllBytes(Paths.get(ALL_ADDRESSES_PATH));
        List<? extends Address> addresses = MAPPER.readValue(jsonData, new TypeReference<List<? extends Address>>() {
        });
        assertEquals(3, addresses.size());
    }

}
