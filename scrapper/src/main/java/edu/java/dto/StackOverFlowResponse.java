package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StackOverFlowResponse {
    @JsonProperty("items")
    private List<StackOverFlowQuestion> items;
}
