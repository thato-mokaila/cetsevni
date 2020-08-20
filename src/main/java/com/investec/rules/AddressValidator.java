package com.investec.rules;

import com.investec.domain.Address;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Abstract validator with a list of standard rules to be applied
 * to every address. This class can be subclasses to add more rules
 * For standard validation, you can instantiate the
 * {@link StandardValidator}
 *
 * @param <T>
 */
public abstract class AddressValidator<T extends Address> {
    /**
     * Rule container
     */
    List<Rule<T>> defaultRules = new ArrayList<>();

    /**
     * Default constructor
     */
    public AddressValidator() {
        this.defaultRules.add(new Rule<>((a) -> Objects.nonNull(a) && Objects.nonNull(a.getAddressLineDetail()) && (Objects.nonNull(a.getAddressLineDetail().getLine1()) || Objects.nonNull(a.getAddressLineDetail().getLine2())),
                "Address requires at one line detail property to be valid"));
        this.defaultRules.add(new Rule<>((a) -> Objects.nonNull(a) && Objects.nonNull(a.getCountry()) && Objects.nonNull(a.getCountry().getCode()),
                "Address requires a country property to be valid"));
        this.defaultRules.add(new Rule<>((a) -> Objects.nonNull(a.getPostalCode())
                && a.getPostalCode().matches("[0-9]+"),
                "Address requires a numeric postal code property to be valid"));
    }

    /**
     * Constructor called from sub-classes to add additional
     * rules to this validator.
     * @param rules additional rules to be evaluated
     */
    public AddressValidator(List<Rule<T>> rules) {
        this();
        this.defaultRules.addAll(rules);
    }

    /**
     * Method that can be called from subclasses with
     * an address to be validated using all rules in
     * this container.
     *
     * @param address to be validated
     * @return list of failed rules
     */
    public List<Rule<T>> validate(final T address) {

        System.out.println("# ----------------------------------------------- ");
        System.out.println("# Rule evaluation for address: [ " +address.getId()+ " | " + address.getType()+ " ] against "+defaultRules.size()+" rules");
        List<Rule<T>> failed = defaultRules.stream().filter(r -> !r.evaluate(address)).collect(Collectors.toList());
        System.out.println("# Rule evaluation result: [ Tested=" + defaultRules.size() + ", Failed=" + failed.size() + " ]");
        System.out.println("# Failed rules " + failed);
        System.out.println("# ----------------------------------------------- ");
        return failed;
    }

    /**
     * Returns a count of all registered rules
     * @return count of rules registered
     */
    public int getRuleCount() {
        return defaultRules.size();
    }
}
