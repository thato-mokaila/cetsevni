package com.investec.ext;

import com.investec.domain.Address;
import com.investec.service.IPrinter;
import lombok.Builder;

@Builder
public class PostalAddress extends Address implements IPrinter {
    @Override
    public String printAddress() {
        // TODO: Parse json instances polymorphic'ly to allow this printing
        return this.getType().getName();
    }
}
