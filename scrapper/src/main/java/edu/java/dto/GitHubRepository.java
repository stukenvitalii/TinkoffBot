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
}
