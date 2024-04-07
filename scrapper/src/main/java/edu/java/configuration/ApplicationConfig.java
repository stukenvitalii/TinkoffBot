package edu.java.configuration;

import edu.java.configuration.access.AccessType;
import edu.java.configuration.retry.RetryStrategy;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@EnableRetry
@EnableKafka
public record ApplicationConfig(
    @NotNull
    @Bean
    Scheduler scheduler,
    @NotNull
    Retry retry,
    @NotNull
    Bucket bucket,
    @NotNull
    Kafka kafka,
    @NotNull
    String gitUrl,
    @NotNull
    String stackUrl,
    @NotNull
    AccessType databaseAccessType,
    @NotNull
    boolean useQueue
) {
    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

    public record Retry(@NotNull String trigger, @NotNull RetryStrategy strategy, @NotNull int maxAttempts,
                        @NotNull int delay) {
    }

    public record Bucket(@NotNull int capacity, @NotNull int refill, @NotNull int timeout) {
    }

    public record Kafka(@NotNull String topics, @NotNull int partitionsNum, @NotNull int replicasNum) {}
}
