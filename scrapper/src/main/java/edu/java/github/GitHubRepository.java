package edu.java.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
public class GitHubRepository {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("defaultBranch")
    private String defaultBranch;

    @JsonProperty("pushed_at")
    private Timestamp lastPush;
}
