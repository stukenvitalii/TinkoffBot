package edu.java.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    private long id;
    private String url;
    private long chatId;
    private Timestamp lastCheckTime;
    private Timestamp createdAt;
}
