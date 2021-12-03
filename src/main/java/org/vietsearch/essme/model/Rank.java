package org.vietsearch.essme.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Rank {
    @JsonProperty("qs")
    @Field("qs")
    String qs;

    @JsonProperty("arwu")
    @Field("arwu")
    String arwu;

    @JsonProperty("the")
    @Field("the")
    String the;
}
