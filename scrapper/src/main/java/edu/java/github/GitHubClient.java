package edu.java.github;

import edu.java.exception.ClientException;
import edu.java.exception.ServerException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.RetryBackoffSpec;

public class GitHubClient {
    private WebClient webClient;
    private final WebClient.Builder webClientBuilder = WebClient.builder();

    @Autowired
    private List<RuntimeException> retryOnExceptions;

    @Autowired
    private RetryBackoffSpec retryBackoffSpec;

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
            .onStatus(
                HttpStatusCode::is5xxServerError,
                response -> Mono.error(new ServerException("Server error", response.statusCode().value()))
            )
            .onStatus(
                HttpStatusCode::is4xxClientError,
                response -> Mono.error(new ClientException("Client error", response.statusCode().value()))
            )
            .bodyToMono(GitHubRepository.class)
            .retryWhen(retryBackoffSpec
                .filter(throwable -> retryOnExceptions.stream()
                    .anyMatch(x -> x.getClass().isAssignableFrom(throwable.getClass()))));
    }
}
