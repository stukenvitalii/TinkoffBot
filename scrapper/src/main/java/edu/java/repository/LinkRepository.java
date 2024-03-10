package edu.java.repository;

import edu.java.model.dto.Link;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class LinkRepository {
    private final JdbcClient jdbcClient;

    @Transactional
    public int add(Link entity) {
        try {
            String sql =
                "insert into link(url, chat_id, last_check_time,created_at) values(:url,:chatId,:lastCheckTime,:createdAt)";

            jdbcClient.sql(sql)
                .param("url", entity.getUrl())
                .param("chatId", entity.getChatId())
                .param("lastCheckTime", entity.getLastCheckTime())
                .param("createdAt", entity.getCreatedAt())
                .update();
        } catch (Exception e) {
            return -1;
        }
        return 1;
    }

    @Transactional
    public int remove(Long id) {
        try {
            String sql = "delete from link where id = ?";
            int count = jdbcClient.sql(sql).param(1, id).update();
            if (count == 0) {
                throw new RuntimeException("link not found");
            }
        } catch (RuntimeException e) {
            return -1;
        }
        return 1;
    }

    @Transactional(readOnly = true)
    public List<Link> findAll() {
        String sql = "select * from link";
        return jdbcClient.sql(sql).query(Link.class).list();
    }

//    @Transactional(readOnly = true)
//    public List<Link> findUnUpdatedLinks() {
//        String sql = "select * from link where datediff(d, last_check_time, current_date) > 30";
//        return jdbcClient.sql(sql).query(Link.class).list();
//    }
    //timestampdiff(SECOND, last_check_time, current_timestamp) > 30
}
