package edu.java.configuration.retryconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;
import java.time.Duration;

public class LinearStrategy {
    //TODO implement linear
//    @Value("${app.retry-max-attempts}")
//    private int maxAttempts;
//
//    @Value("${app.retry-delay}")
//    private int delay;
//
//    @Bean
//    public RetryBackoffSpec retryBackoffSpec() {
//        return Retry. (maxAttempts, Duration.ofSeconds(delay));
//    }
}
