package org.vietsearch.essme.model.expert;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Location{

	@JsonProperty("features")
	private List<FeaturesItem> features;

	@JsonProperty("type")
	private String type = "FeatureCollection";
}
