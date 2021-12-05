package org.vietsearch.essme.model.companies;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Ranks {
    @Field("Forbes")
    @JsonProperty("Forbes")
    private int forbes;

    @Field("Fortune")
    @JsonProperty("Fortune")
    private int fortune;

    @Field("ValueToday")
    @JsonProperty("ValueToday")
    private int valueToday;
}