package edu.java.service.jooq;

import edu.java.model.dto.Chat;
import edu.java.repository.jooq.JooqChatRepository;
import edu.java.service.ChatService;
import java.util.List;

public class JooqChatService implements ChatService {
    private final JooqChatRepository jooqChatRepository;

    public JooqChatService(JooqChatRepository jooqChatRepository) {
        this.jooqChatRepository = jooqChatRepository;
    }

    @Override
    public List<Chat> getChats() {
        return jooqChatRepository.findAll();
    }

    @Override
    public void addChat(Chat chat) {
        jooqChatRepository.add(chat);
    }

    @Override
    public void removeChat(Long id) {
        jooqChatRepository.remove(id);
    }
}
