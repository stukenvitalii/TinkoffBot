package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class StackOverFlowQuestion {
    @JsonProperty("question_id")
    private Long questionId;

    @JsonProperty("is_answered")
    private boolean isAnswered;

    @JsonProperty("title")
    private String title;
}
