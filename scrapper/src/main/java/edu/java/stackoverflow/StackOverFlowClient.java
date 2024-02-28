package edu.java.stackoverflow;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class StackOverFlowClient {
    private final WebClient webClient;
    private static final String URL = "/questions/%d?order=%s&sort=%s&site=stackoverflow";

    public StackOverFlowClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<StackOverFlowResponse> fetchQuestion(long questionId, String sort, String order) {
        String apiUrl = String.format(URL, questionId, sort, order);

        return webClient
            .get()
            .uri(apiUrl)
            .retrieve()
            .bodyToMono(StackOverFlowResponse.class);
    }
}
