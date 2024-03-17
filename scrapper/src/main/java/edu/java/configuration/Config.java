package edu.java.configuration;

import org.jooq.conf.RenderQuotedNames;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public DefaultConfigurationCustomizer configurationCustomizer() {
        return (DefaultConfiguration c) -> c.settings()
            .withRenderQuotedNames(
                RenderQuotedNames.EXPLICIT_DEFAULT_UNQUOTED
            );
    }
}
