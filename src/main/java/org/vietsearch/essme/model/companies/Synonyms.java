package org.vietsearch.essme.model.companies;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Synonyms {

    @JsonProperty("de")
    private List<String> de;

    @JsonProperty("vi")
    private List<Object> vi;

    @JsonProperty("en")
    private List<String> en;

    @JsonProperty("fr")
    private List<Object> fr;
}