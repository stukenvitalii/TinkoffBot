package edu.java.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class GitHubRepository {
    private Long id;
    private String name;
    private String defaultBranch;

    public GitHubRepository(Long id, String name, String defaultBranch) {
        this.id = id;
        this.name = name;
        this.defaultBranch = defaultBranch;
    }

}
