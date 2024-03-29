package edu.java.configuration;

import edu.java.configuration.retryconfig.RetryStrategy;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@EnableRetry
public record ApplicationConfig(
    @NotNull
    @Bean
    Scheduler scheduler,
    @NotNull
    String gitUrl,
    @NotNull
    String stackUrl,
    @NotNull
    AccessType databaseAccessType,
    @NotNull
    String retryOn,
    @NotNull
    RetryStrategy retryStrategy,
    @NotNull
    int retryMaxAttempts,
    @NotNull
    int retryDelay) {
    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }
}
