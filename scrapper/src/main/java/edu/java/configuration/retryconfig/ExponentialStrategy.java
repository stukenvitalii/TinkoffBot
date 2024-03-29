package edu.java.configuration.retryconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;
import java.time.Duration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "retry-strategy", havingValue = "exponential")
public class ExponentialStrategy {
    @Value("${app.retry-max-attempts}")
    private int maxAttempts;

    @Value("${app.retry-delay}")
    private int delay;

    @Bean
    public RetryBackoffSpec retryBackoffSpec() {
        return Retry.backoff(maxAttempts, Duration.ofSeconds(delay));
    }
}
