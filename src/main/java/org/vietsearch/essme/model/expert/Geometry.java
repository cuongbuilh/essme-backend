package org.vietsearch.essme.model.expert;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Geometry{

	@JsonProperty("coordinates")
	private List<Double> coordinates;

	@JsonProperty("type")
	private String type = "Point";
}