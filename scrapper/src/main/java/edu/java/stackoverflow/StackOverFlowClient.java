package edu.java.stackoverflow;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class StackOverFlowClient {

    private WebClient webClient;
    private final WebClient.Builder webClientBuilder = WebClient.builder();
    private static final String URL = "/questions/%d?site=stackoverflow";

    public StackOverFlowClient(String baseurl) {
        webClient = webClientBuilder.baseUrl(baseurl)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE).build();
    }

    public Mono<StackOverFlowResponse> fetchQuestion(long questionId) {
        String apiUrl = String.format(URL, questionId);

        return webClient
            .get()
            .uri(apiUrl)
            .retrieve()
            .bodyToMono(StackOverFlowResponse.class);
    }
}
