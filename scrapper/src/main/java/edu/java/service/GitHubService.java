package edu.java.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GitHubService {
    private final WebClient webClient;

    public GitHubService() {
        this.webClient = WebClient.create("https://api.github.com");
    }


    public Mono<String> getRepositoryJson(String owner, String repoName) {
        return this.webClient.get()
            .uri("/repos/{owner}/{repo}", owner, repoName)
            .retrieve()
            .bodyToMono(String.class);
    }
}
