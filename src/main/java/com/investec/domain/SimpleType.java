package com.investec.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@JsonRootName("type")
public class SimpleType {

    private String code;
    private String name;

    @JsonCreator
    public SimpleType(
            @JsonProperty("code") String code,
            @JsonProperty("name") String name
    ) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
