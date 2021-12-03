package org.vietsearch.essme.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("universities")
public class University {
    @Id
    @JsonProperty(value = "_id", access = JsonProperty.Access.READ_ONLY)
    private String _id;

    @JsonProperty("id")
    @Field("id")
    private String id;

    @JsonProperty("name")
    @Field("name")
    private String name;
    //synonyms
    @JsonProperty("synonyms")
    @Field("synonyms")
    private Synonym synonyms;

    @JsonProperty("website")
    @Field("website")
    private String website;

    @JsonProperty("country")
    @Field("country")
    private String country;

    @JsonProperty("ranks")
    @Field("ranks")
    private Rank ranks;

    @JsonProperty("types")
    private List<String> types;

    @JsonProperty("last_updated")
    @Field("last_updated")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastUpdate;
    // names
    @JsonProperty("names")
    @Field("names")
    private Names names;

    @JsonProperty("source")
    @Field("source")
    private String source;


}
