package org.vietsearch.essme.model.academic_rank;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Synonyms {

    @JsonProperty("vi")
    private List<String> vi;

    @JsonProperty("en")
    private List<String> en;
}