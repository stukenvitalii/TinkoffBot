package edu.java.service.jdbc;

import edu.java.model.dto.Chat;
import edu.java.repository.jdbc.JdbcChatRepository;
import edu.java.service.ChatService;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JdbcChatService implements ChatService {

    private final JdbcChatRepository jdbcChatRepository;

    @Override
    public List<Chat> getChats() {
        return jdbcChatRepository.findAll();
    }

    @Override
    public void addChat(Chat chat) {
        jdbcChatRepository.add(chat);
    }

    @Override
    public void removeChat(Long id) {
        jdbcChatRepository.remove(id);
    }
}
