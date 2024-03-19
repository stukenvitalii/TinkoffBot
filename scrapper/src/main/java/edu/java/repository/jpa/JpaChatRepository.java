package edu.java.repository.jpa;

import edu.java.model.dto.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
public interface JpaChatRepository extends JpaRepository<Chat, Long> {
    Chat save(Chat chatEntity);

    void removeById(Long chatId);

    List<Chat> findAll();
}
