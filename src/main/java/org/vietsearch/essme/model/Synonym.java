package org.vietsearch.essme.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Synonym {
    @JsonProperty("de")
    private List<String> de;

    @JsonProperty("en")
    private List<String> en;

    @JsonProperty("fr")
    private List<String> fr;

    @JsonProperty("vi")
    private List<String> vi;
}
