package edu.java.service.jdbc;

import edu.java.model.dto.Chat;
import edu.java.repository.ChatRepository;
import edu.java.service.ChatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcChatService implements ChatService {

    private final ChatRepository chatRepository;

    @Override
    public List<Chat> getChats() {
        return chatRepository.findAll();
    }

    @Override
    public void addChat(Chat chat) {
        chatRepository.add(chat);
    }

    @Override
    public void removeChat(Long id) {
        chatRepository.remove(id);
    }
}
