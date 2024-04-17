package edu.java.configuration.access;

import edu.java.repository.jooq.JooqChatRepository;
import edu.java.repository.jooq.JooqLinkRepository;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import edu.java.service.jooq.JooqChatService;
import edu.java.service.jooq.JooqLinkService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfiguration {
    @Bean
    public LinkService linkService(
        JooqLinkRepository linkRepository
    ) {
        return new JooqLinkService(linkRepository);
    }

    @Bean
    public ChatService chatService(
        JooqChatRepository chatRepository
    ) {
        return new JooqChatService(chatRepository);
    }
}
