package edu.java.github;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class GitHubClient {
    private WebClient webClient;
    private final WebClient.Builder webClientBuilder = WebClient.builder();

    public GitHubClient(String baseurl) {
        webClient = webClientBuilder.baseUrl(baseurl)
            .defaultHeaders(h -> h.setBearerAuth(System.getenv("GITHUB_API_TOKEN")))
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE).build();
    }

    public Mono<GitHubRepository> getRepositoryInfo(String name, String reposName) {
        return webClient
            .get()
            .uri(x -> x.path("/repos/{name}/{reposName}")
                .build(name, reposName))
            .retrieve()
            .bodyToMono(GitHubRepository.class);
    }
}
