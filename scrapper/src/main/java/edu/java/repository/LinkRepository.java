package edu.java.repository;

import edu.java.model.dto.Link;
import java.sql.Timestamp;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class LinkRepository {
    private final JdbcClient jdbcClient;

    @Transactional
    public void add(Link entity) {

        String sql =
            "insert into link(url, chat_id, last_check_time,created_at) values(:url,:chatId,:lastCheckTime,:createdAt)";

        jdbcClient.sql(sql)
            .param("url", entity.getUrl())
            .param("chatId", entity.getChatId())
            .param("lastCheckTime", entity.getLastCheckTime())
            .param("createdAt", entity.getCreatedAt())
            .update();
    }

    @Transactional
    public void remove(Long id) {

        String sql = "delete from link where id = ?";
        int count = jdbcClient.sql(sql).param(1, id).update();
        if (count == 0) {
            throw new RuntimeException("link not found");
        }
    }

    @Transactional(readOnly = true)
    public List<Link> findAll() {
        String sql = "select * from link";
        return jdbcClient.sql(sql).query(Link.class).list();
    }

    @Transactional
    public void updateLinkLastCheckTimeById(Long id, Timestamp lastCheckTime) {
        String sql =
            "update link set  last_check_time = (:lastCheckTime) where id = :link_id";
        jdbcClient.sql(sql)
            .param("link_id", id)
            .param("lastCheckTime", lastCheckTime)
            .update();
    }

    @Transactional(readOnly = true)
    public List<Link> getUnUpdatedLinks() {
        String sql = "select * from link where EXTRACT(SECOND FROM (now() - last_check_time )) > 30";
        return jdbcClient.sql(sql).query(Link.class).list();
    }
}
