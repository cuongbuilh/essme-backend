package org.vietsearch.essme.model.expert;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Document(collection = "experts_vn")
public class Expert {

    @Id
    @JsonProperty("_id")
    private String _id;

    @JsonProperty("image")
    private String image;

    @Field(name = "other link")
    @JsonProperty("other link")
    private String otherLink;

    @JsonProperty("address")
    private String address;

    @JsonProperty("gender")
    private int gender;

    @JsonProperty("degree")
    private String degree;

    @NotNull
    @Field(name = "degree index")
    @JsonProperty("degree index")
    private double degreeIndex;

    @JsonProperty("birth")
    private String birth;

    @NotBlank
    @JsonProperty("phone")
    private String phone;

    @NotBlank
    @JsonProperty("name")
    private String name;

    @Field(name = "research area")
    @JsonProperty("research area")
    private String researchArea;

    @JsonProperty("company")
    private String company;

    @JsonProperty("location")
    private Location location;

    @NotBlank
    @JsonProperty("email")
    private String email;

    @Field(name = "link profile")
    @JsonProperty("link profile")
    private String linkProfile;
}