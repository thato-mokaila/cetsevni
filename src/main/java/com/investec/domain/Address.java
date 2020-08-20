package com.investec.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.investec.BusinessException;
import com.investec.service.IAddress;
import com.investec.service.IPrinter;
import com.investec.util.JsonDateDeserializer;
import com.investec.util.JsonDateSerializer;
import com.investec.util.MapperFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
// @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
// @JsonSubTypes({ @JsonSubTypes.Type(value = PhysicalAddress.class) })
public class Address implements IAddress, IPrinter {

    private String id;
    private SimpleType type;
    private AddressLineDetail addressLineDetail;
    private String postalCode;
    private String cityOrTown;
    private SimpleType country;
    private SimpleType provinceOrState;
    private SuburbOrDistrict suburbOrDistrict;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime lastUpdated;

    @Override
    public String prettyPrintAddress() throws BusinessException {
        try {
            return MapperFactory.getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new BusinessException("Unable to pretty print address [" + this + "] ");
        }
    }

    @Override
    public String formattedAddress() {
        return String.format(PRINTER_FORMAT,
                this.type.getName(),
                this.addressLineDetail != null ? this.addressLineDetail.getLine1().concat(", ").concat(this.getAddressLineDetail().getLine2()) : "Not provided",
                this.cityOrTown != null ? this.cityOrTown : "Not provided",
                this.provinceOrState != null ? this.provinceOrState.getName() : "Not provided",
                this.postalCode != null ? postalCode : "Not provided",
                this.country != null ? country.getName() : "Not provided"
        );
    }

    @Override
    public String printAddress() {
        return this.toString();
    }
}
