package edu.java.model.dto;

import java.net.URI;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    private long id;
    private URI url;
    private long chatId;
    private Timestamp lastCheckTime;
    private Timestamp createdAt;
}
