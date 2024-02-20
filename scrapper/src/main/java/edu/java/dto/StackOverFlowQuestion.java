package edu.java.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class StackOverFlowQuestion {
    private Long question_id;
    private boolean is_answered;
    private String title;

    public StackOverFlowQuestion(Long question_id, boolean is_answered, String title) {
        this.question_id = question_id;
        this.is_answered = is_answered;
        this.title = title;
    }
}
