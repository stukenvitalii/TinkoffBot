package edu.java.configuration.retryconfig;

import java.time.Duration;
import java.util.List;
import org.reactivestreams.Publisher;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

public class RetryConfig extends Retry {
    private final int retries;
    private final Duration firstTime;
    private final List<Throwable> retryOnExceptions;

    public RetryConfig(int retries, Duration firstTime, List<Throwable> retryOnExceptions) {
        this.retries = retries;
        this.firstTime = firstTime;
        this.retryOnExceptions = retryOnExceptions;
    }

    @Override
    public Publisher<?> generateCompanion(Flux<RetrySignal> retrySignals) {
        return retrySignals.flatMap(this::getRetry);
    }

    private Mono<Long> getRetry(RetrySignal rs) {
        if (rs.totalRetries() < retries) {
            Duration delay = firstTime;
            delay = delay.multipliedBy(rs.totalRetries());

            return Mono.delay(delay)
                .thenReturn(rs.totalRetries());
        } else {
            throw Exceptions.propagate(rs.failure());
        }
    }

    private boolean shouldRetry(Throwable throwable) {
        return retryOnExceptions.stream()
            .anyMatch(exceptionClass -> exceptionClass.getClass().isAssignableFrom(throwable.getClass()));
    }

    public RetryConfig filter(List<Throwable> retryOnExceptions) {
        return this;
    }
}
