package org.vietsearch.essme.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Names {
    @JsonProperty("de")
    private String de;

    @JsonProperty("en")
    private String en;

    @JsonProperty("fr")
    private String fr;

    @JsonProperty("vi")
    private String vi;
}
