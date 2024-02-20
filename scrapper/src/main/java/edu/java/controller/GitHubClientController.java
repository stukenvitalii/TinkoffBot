package edu.java.controller;

import edu.java.dto.GitHubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Arrays;
import java.util.List;

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
            .uri("https://api.github.com/repos/" + name + "/" + reposName)
            .headers(h -> h.setBearerAuth(System.getenv("GITHUB_API_TOKEN_SECOND")))//TODO РАЗБИТЬ КРАСИВО
            .retrieve()
            .bodyToMono(GitHubRepository.class)
            .block();
    }

    @GetMapping("/{name}/repos")
    public Mono<List<GitHubRepository>> getRepositories(@PathVariable("name") String name) {
        return webClientBuilder.build()
            .get()
            .uri("https://api.github.com/users/" + name + "/repos")
            .headers(h -> h.setBearerAuth(System.getenv("GITHUB_API_TOKEN_SECOND")))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(GitHubRepository[].class)
            .map(Arrays::asList);
    }
}
