package org.vietsearch.essme.model.companies;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Names {

    @JsonProperty("de")
    private String de;

    @JsonProperty("vi")
    private String vi;

    @JsonProperty("en")
    private String en;

    @JsonProperty("fr")
    private String fr;
}