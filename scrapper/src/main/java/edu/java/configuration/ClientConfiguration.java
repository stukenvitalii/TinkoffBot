package edu.java.configuration;

import edu.java.github.GitHubClient;
import edu.java.stackoverflow.StackOverFlowClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

    @Value("${app.gitUrl}")
    private String gitUrl;

    @Bean
    public GitHubClient gitHubClient() {
        return new GitHubClient(gitUrl);
    }

    @Value("${app.stackUrl}")
    private String stackUrl;

    @Bean
    public StackOverFlowClient stackOverFlowClient() {
        return new StackOverFlowClient(stackUrl);
    }
}
