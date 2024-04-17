package edu.java.service.jpa;

import edu.java.model.dto.Chat;
import edu.java.repository.jpa.JpaChatRepository;
import edu.java.service.ChatService;
import java.util.List;

public class JpaChatService implements ChatService {
    private final JpaChatRepository jpaChatRepository;

    public JpaChatService(JpaChatRepository jpaChatRepository) {
        this.jpaChatRepository = jpaChatRepository;
    }

    @Override
    public List<Chat> getChats() {
        return jpaChatRepository.findAll();
    }

    @Override
    public void addChat(Chat chat) {
        jpaChatRepository.save(chat);
    }

    @Override
    public void removeChat(Long id) {
        jpaChatRepository.removeById(id);
    }
}
