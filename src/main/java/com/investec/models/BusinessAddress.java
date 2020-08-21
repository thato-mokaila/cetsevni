package com.investec.models;

import com.investec.service.IPrinter;
import lombok.Builder;

@Builder
public class BusinessAddress extends Address implements IPrinter {
    @Override
    public String printAddress() {
        // TODO: Parse json instances polymorphic'ly to allow this printing
        return this.getType().getName();
    }

}
