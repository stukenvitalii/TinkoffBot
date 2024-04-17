package edu.java.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RetryOnExceptionGetter {
    @Value("${app.retryOn}")
    private String retryOnProperty;

    @Bean
    public List<RuntimeException> getExceptionList() {
        List<RuntimeException> exceptions = new ArrayList<>();
        List<String> tokens = Arrays.stream(retryOnProperty.toLowerCase(Locale.ROOT).split("-")).toList();
        if (tokens.contains("client")) {
            exceptions.add(new ClientException("Client error"));
        }
        if (tokens.contains("server")) {
            exceptions.add(new ServerException("Server error"));
        }
        return exceptions;
    }

}
