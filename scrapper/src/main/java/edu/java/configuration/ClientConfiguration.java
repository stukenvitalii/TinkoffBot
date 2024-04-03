package edu.java.configuration;

import edu.java.client.BotClient;
import edu.java.github.GitHubClient;
import edu.java.stackoverflow.StackOverFlowClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.util.retry.RetryBackoffSpec;

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

    @Autowired
    private RetryBackoffSpec retryBackoffSpec;

    @Bean
    @ConditionalOnProperty(value = "app.use-queue", havingValue = "false", matchIfMissing = true)
    public BotClient botClient() {
        return new BotClient(retryBackoffSpec);
    }
}
