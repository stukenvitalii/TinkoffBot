package edu.java.controller;

import edu.java.dto.GitHubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class GitHubClientController {

    @Qualifier("gitHubClient")
    @Autowired
    private WebClient webClient;

    @GetMapping("/repos/{name}/{reposName}")
    public GitHubRepository getRepositoryInfo(
        @PathVariable("name") String name,
        @PathVariable("reposName") String reposName
    ) {
        return webClient
            .get()
            .uri(x-> x.path("/repos/{name}/{reposName}")
                .build(name, reposName))
            .retrieve()
            .bodyToMono(GitHubRepository.class)
            .block();
    }
}
