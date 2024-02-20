package edu.java.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GitHubRepository {
    private Long id ;
    private String name;
    private String defaultBranch;

    public GitHubRepository(Long id , String name, String defaultBranch){
        this.id = id ;
        this.name = name;
        this.defaultBranch = defaultBranch;
    }


}
