package edu.java.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient gitHubClient(@Value("${app.git-hub-base-url}") String baseUrl,
        WebClient.Builder webClientBuilder) {
        return webClientBuilder
            .baseUrl(baseUrl)
            .defaultHeaders(h -> h.setBearerAuth(System.getenv("GITHUB_API_TOKEN_SECOND")))
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    @Bean
    public WebClient stackOverFlowClient(@Value("${app.stack-overflow-base-url}") String baseUrl,
        WebClient.Builder webClientBuilder) {
        return webClientBuilder
            .baseUrl(baseUrl)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }
}
