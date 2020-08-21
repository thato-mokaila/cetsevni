package com.investec.rules;

import com.investec.models.Address;

import java.util.List;

public class ZAValidator extends AddressValidator<Address> {
    public ZAValidator(List<Rule<Address>> additionalRules) {
        super(additionalRules);
    }
}
