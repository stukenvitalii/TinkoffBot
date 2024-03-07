package edu.java.bot.configuration;

import edu.java.bot.url_processor.GitHubUrlProcessor;
import edu.java.bot.url_processor.StackOverflowUrlProcessor;
import edu.java.bot.url_processor.UrlProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class URLProcessorConfig {

    @Bean
    UrlProcessor urlProcessor() {
        return new StackOverflowUrlProcessor(
            new GitHubUrlProcessor(null)
        );
    }
}
