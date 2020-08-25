package com.investec;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.investec.models.Address;
import com.investec.models.SimpleType;
import com.investec.rules.AddressValidator;
import com.investec.rules.Rule;
import com.investec.rules.StandardValidator;
import com.investec.rules.ZAValidator;
import com.investec.util.MapperFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author thato
 * Read and parse addresses from json file.
 */
public class App {

    private static final String PATH = "src/main/resources/addresses.json";
    private final List<Address> addresses = new ArrayList<>();

    /**
     * Reads and parses addresses json into {@code Address}
     * objects.
     *
     * @return list of {@code Address}s
     */
    public List<Address> loadAddresses(String path) throws BusinessException {
        try {

            byte[] jsonData = Files.readAllBytes(Paths.get(path));
            ObjectMapper objectMapper = MapperFactory.getMapper();
            List<Address> list = objectMapper
                    .readValue(jsonData, new TypeReference<List<Address>>(){});

            addresses.addAll(list);

        } catch (NoSuchFileException ex) {
            ex.printStackTrace();
            throw new BusinessException("Path to file does not exist json");
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new BusinessException("Unable to read address json for path: " + path);
        }
        return addresses;
    }

    /**
     * Returns a formatted string of this address.
     *
     * @param address address to be printed
     * @return string with formatted printing
     */
    public String formattedPrinting(Address address) {
        return address.formattedAddress();
    }

    /**
     * Print addresses by type.
     *
     * @param type address to be printed
     */
    public void printAddressesByType(SimpleType type) {
        addresses.stream().filter(a -> type.equals(a.getType())).forEach(System.out::println);
    }

    /**
     * Returns a pretty print of the supplied address.
     *
     * @param address address to be pretty printed
     * @return string with formatted printing
     */
    public String prettyPrintAddress(Address address) throws BusinessException {
        return address.prettyPrintAddress();
    }

    /**
     * Pretty prints all addresses in the supplied list.
     *
     * @param addresses list of addresses to be pretty printed
     */
    public <T extends Address> void prettyPrintAddresses(List<T> addresses) {
        addresses.forEach(s -> {
            try {
                System.out.println(this.prettyPrintAddress(s));
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Validates a given list of addresses.
     *
     * @param addresses list of target types on which the rules will be applied
     * @param validators list of validators to use
     * @param <T> type of address
     */
    public <T extends Address> Map<Address, List<Rule<T>>> validateAddresses(final List<T> addresses, final List<? extends AddressValidator<T>> validators) {
        // Go through the address list and validate each address against the
        // provided validators
        Map<Address, List<Rule<T>>> failedRules = new HashMap<>();
        addresses.forEach(address -> {
            List<Rule<T>> results = validators.iterator().next().validate(address);
            failedRules.putIfAbsent(address, results);
        });
        return failedRules;
    }

    /**
     * Validates the given single address instance against the supplied
     * validator.
     *
     * @param address target type on which the rules will be applied
     * @param validator a validators to use
     * @param <T> type of address
     * @return list containing failed rules, empty if all rules passed
     */
    public <T extends Address> List<Rule<T>> validateAddress(T address, AddressValidator<T> validator) {
        return validator.validate(address);
    }

    public static void main( String[] args ) throws BusinessException {

        App app = new App();

        // Read addresses from json doc
        List<Address> addresses = app.loadAddresses(PATH);

        // Create a ZA validator with additional rules that are specific to ZA addresses
        ZAValidator validator = new ZAValidator(
                Collections.singletonList(
                        new Rule<>(a -> Objects.nonNull(a) && Objects.nonNull(a.getProvinceOrState()), "(ZA) Address requires a province or state property to be valid"))
        );

        System.out.println("\n# ##################### Validate Multiple Addresses #####################\n");
        // Validate all addresses against default validation rules + custom rule
        Map<Address, List<Rule<Address>>> failed1 = app.validateAddresses(addresses, Collections.singletonList(validator));
        if (!failed1.isEmpty()) {
            // System.out.println(failed1);
            System.out.println("\n# ##################### Validate Multiple Addresses #####################\n");
        }

        // Validate a single address against default validation rules
        if (!addresses.isEmpty()) {
            System.out.println("\n# ##################### Validate Single Address #####################\n");
            List<Rule<Address>> failed2 = app.validateAddress(addresses.get(1), new StandardValidator());
            System.out.println("\n# ##################### Validate Single Address #####################\n");
        }

        // Pretty printing of an address using specific format
        if (!addresses.isEmpty()) {
            System.out.println("\n# ##################### Pretty Print Single Address #####################\n");
            try {
                System.out.println(app.prettyPrintAddress(addresses.get(new Random().nextInt(addresses.size()))));
            } catch (BusinessException e) {
                e.printStackTrace();
            }
            System.out.println("\n# ##################### Pretty Print Single Address #####################\n");
        }

        // Pretty printing of all addresses
        if (!addresses.isEmpty()) {
            System.out.println("\n# ##################### Pretty Print Multiple Addresses #####################\n");
            app.prettyPrintAddresses(addresses);
            System.out.println("\n# ##################### Pretty Print Multiple Addresses #####################\n");
        }

        // Printing of all addresses types
        if (!addresses.isEmpty()) {
            System.out.println("\n# ##################### Printing all Address types #####################\n");
            String out = app.formattedPrinting(addresses.get(new Random().nextInt(addresses.size())));
            System.out.println(out);
            System.out.println("\n# ##################### Printing all Address types #####################\n");
        }

        // Printing an addresses type
        if (!addresses.isEmpty()) {
            System.out.println("\n# ##################### Printing addresses by types #####################\n");
            app.printAddressesByType(SimpleType.builder().code("1").name("Physical Address").build());
            System.out.println("\n# ##################### Printing addresses by types #####################\n");
        }

    }

}
