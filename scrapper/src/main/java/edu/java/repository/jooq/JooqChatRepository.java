package edu.java.repository.jooq;

import edu.java.model.dto.Chat;
import edu.java.model.dto.Link;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import java.util.List;
import static edu.java.domain.jooq.Tables.CHAT;
import static edu.java.domain.jooq.Tables.LINK;

@Repository
public class JooqChatRepository {
    private final DSLContext dslContext;

    public JooqChatRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void add(Chat entity) {
        dslContext
            .insertInto(CHAT)
            .set(CHAT.ID, entity.getId())
            .set(CHAT.CHAT_ID, entity.getChatId())
            .returning(CHAT)
            .fetchOne();
    }

    public List<Chat> findAll() {
        return dslContext.selectFrom(CHAT).fetchInto(Chat.class);
    }

    public void remove(Long id) {
        dslContext.deleteFrom(CHAT)
            .where(CHAT.ID.equal(id))
            .execute();
    }
}
