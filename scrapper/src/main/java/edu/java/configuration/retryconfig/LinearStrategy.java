package edu.java.configuration.retryconfig;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;
import java.time.Duration;
import java.util.List;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "retry-strategy", havingValue = "linear")
public class LinearStrategy {
    //TODO implement linear
    @Value("${app.retry-max-attempts}")
    private int maxAttempts;

    @Value("${app.retry-delay}")
    private int delay;

    @Autowired
    private List<RuntimeException> retryOnExceptions;

//    @Bean
//    public Retry retryBackoffSpec() {
//        return new RetryConfig(maxAttempts, Duration.ofSeconds(delay)).filter(retryOnExceptions, throwable -> retryOnExceptions.stream()
//            .anyMatch(x -> x.getClass().isAssignableFrom(throwable.getClass())));
//    }
}
