package edu.java.bot.configuration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotEmpty String telegramToken, @NotNull
Kafka kafka) {
    public record Kafka(@NotNull String topic, @NotNull String topic_dlq, @NotNull int partitionsNum, @NotNull int replicasNum) {
    }
}

