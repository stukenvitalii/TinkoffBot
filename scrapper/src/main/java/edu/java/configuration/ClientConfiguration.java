package edu.java.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
            .baseUrl("https://api.github.com")
                .defaultHeader("Authorization", "Bearer YOUR_ACCESS_TOKEN");
    }

//    @Bean
//    public StackOverFlowService stackOverFlowService() {
//        return new StackOverFlowService();
//    }

}
