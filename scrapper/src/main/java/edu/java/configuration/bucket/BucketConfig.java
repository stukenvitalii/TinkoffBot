package edu.java.configuration.bucket;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BucketConfig {
    @Value("${app.bucket.capacity}")
    int capacity;

    @Value("${app.bucket.refill}")
    int refill;

    @Value("${app.bucket.timeout}")
    int timeout;

    @Bean
    public Bucket bucket() {
        Bandwidth limit = Bandwidth.classic(capacity, Refill.greedy(refill, Duration.ofMinutes(timeout)));
        return Bucket.builder()
            .addLimit(limit)
            .build();
    }
}
