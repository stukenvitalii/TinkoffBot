package edu.java.configuration.retryconfig;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

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
}
