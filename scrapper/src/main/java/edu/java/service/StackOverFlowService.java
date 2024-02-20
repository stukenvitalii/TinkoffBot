package edu.java.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class StackOverFlowService {

    private final WebClient webClient;
    private final String baseUrl = "https://api.stackexchange.com";

    public StackOverFlowService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }
}
