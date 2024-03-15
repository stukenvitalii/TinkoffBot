package edu.java.stackoverflow;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

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

    @JsonProperty("last_activity_date")
    private Long last_activity;

    @JsonProperty("answer_count")
    private int answerCount;

    public Timestamp getLastActivityAsTimestamp() {
        return Timestamp.valueOf(Instant.ofEpochMilli(last_activity*1000).atZone(ZoneId.systemDefault()).toLocalDateTime());
    }
}
