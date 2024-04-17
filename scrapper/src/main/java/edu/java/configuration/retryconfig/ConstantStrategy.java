package edu.java.configuration.retryconfig;

import java.time.Duration;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "retry-strategy", havingValue = "const")
public class ConstantStrategy {
    @Value("${app.retry-max-attempts}")
    private int maxAttempts;

    @Value("${app.retry-delay}")
    private int delay;

    @Autowired
    private List<RuntimeException> retryOnExceptions;

    @Bean
    public RetryBackoffSpec retryBackoffSpec() {
        return Retry.fixedDelay(maxAttempts, Duration.ofSeconds(delay)).filter(throwable -> retryOnExceptions.stream()
            .anyMatch(x -> x.getClass().isAssignableFrom(throwable.getClass())));
    }
}
