package org.vietsearch.essme.model.academic_rank;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("academic_rank")
public class AcademicRank {
    @Id
    @JsonProperty("_id")
    private String _id;

    @JsonProperty("synonyms")
    private Synonyms synonyms;

    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private String id;

}