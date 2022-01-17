package org.vietsearch.essme.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("web_footer")
public class Footer {

    @Id
    @JsonProperty(value = "_id", access = JsonProperty.Access.READ_ONLY)
    private String id;

    @JsonProperty("twitter")
    private String twitter;

    @JsonProperty("terms")
    private String terms;

    @JsonProperty("facebook")
    private String facebook;

    @JsonProperty("contact")
    private String contact;

    @JsonProperty("team")
    private String team;

    @JsonProperty("linkedin")
    private String linkedin;

    @JsonProperty("slogan")
    private String slogan;
}