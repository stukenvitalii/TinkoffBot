package edu.java.repository;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GitHubRepository {
    public GitHubRepository() {
    }

    private String name;

    private String description;
}
