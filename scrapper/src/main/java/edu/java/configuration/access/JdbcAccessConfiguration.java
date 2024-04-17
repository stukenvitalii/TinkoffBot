package edu.java.configuration.access;

import edu.java.repository.jdbc.JdbcChatRepository;
import edu.java.repository.jdbc.JdbcLinkRepository;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import edu.java.service.jdbc.JdbcChatService;
import edu.java.service.jdbc.JdbcLinkService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public LinkService linkService(
        JdbcLinkRepository linkRepository
    ) {
        return new JdbcLinkService(linkRepository);
    }

    @Bean
    public ChatService chatService(
        JdbcChatRepository chatRepository
    ) {
        return new JdbcChatService(chatRepository);
    }
}
