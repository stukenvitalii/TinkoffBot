package edu.java.controller;

import edu.java.dto.GitHubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class GitHubClientController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/repos/{name}/{reposName}")
    public GitHubRepository getRepositoryInfo(
        @PathVariable("name") String name,
        @PathVariable("reposName") String reposName
    ) {
        return webClientBuilder.build()
            .get()
            .uri("https://api.github.com/repos/{name}/{reposName}", name, reposName)
            .headers(h -> h.setBearerAuth(System.getenv("GITHUB_API_TOKEN_SECOND")))//TODO РАЗБИТЬ КРАСИВО
            .retrieve()
            .bodyToMono(GitHubRepository.class)
            .block();
    }
}
