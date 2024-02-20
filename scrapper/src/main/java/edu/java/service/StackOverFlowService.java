package edu.java.service;

import edu.java.repository.GitHubRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class StackOverFlowService {

    private final WebClient webClient;
    private final String BASE_URL = "https://api.github.com";

    public StackOverFlowService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(BASE_URL).build();
    }

    public Mono<String> someRestCall(String name) {
        return this.webClient.get().uri("/{name}/details", name).retrieve().bodyToMono(String.class);
    }



}
