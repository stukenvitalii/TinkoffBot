package edu.java.repository;

import edu.java.model.dto.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRepository {
    private final JdbcClient jdbcClient;

    @Transactional
    public void add(Chat chatEntity) {
        String sql =
            "insert into chat(chat_id) values(:chatId)";
        jdbcClient.sql(sql)
            .param("chatId", chatEntity.getChatId())
            .update();
    }

    @Transactional
    public void remove(Long chat_id) {
        String sql = "delete from chat where chat_id = ?";
        int count = jdbcClient.sql(sql).param(1, chat_id).update();
        if (count == 0) {
            throw new RuntimeException("chat not found");
        }
    }

    @Transactional(readOnly = true)
    public List<Chat> findAll() {
        String sql = "select * from chat";
        return jdbcClient.sql(sql).query(Chat.class).list();
    }
}
