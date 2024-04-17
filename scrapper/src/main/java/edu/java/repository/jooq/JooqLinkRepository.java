package edu.java.repository.jooq;

import edu.java.model.dto.Link;
import edu.java.model.dto.LinkSof;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static edu.java.domain.jooq.Tables.LINK;
import static edu.java.domain.jooq.Tables.SOF_LINK_PROPERTIES;

@Repository
public class JooqLinkRepository {

    @Autowired
    private final DSLContext dslContext;

    public JooqLinkRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void add(Link entity) {
        dslContext.insertInto(LINK)
            .set(LINK.URL, entity.getUrl().toString())
            .set(LINK.CHAT_ID, entity.getChatId())
            .set(LINK.LAST_CHECK_TIME, offsetDateTimeFromTimestamp(entity.getLastCheckTime()))
            .set(LINK.CREATED_AT, offsetDateTimeFromTimestamp(entity.getCreatedAt()))
            .returning(LINK)
            .fetchOne();
    }

    public List<Link> findAll() {
        return dslContext.selectFrom(LINK).fetchInto(Link.class);
    }

    public void remove(Long id) {
        dslContext.deleteFrom(LINK)
            .where(LINK.ID.equal(id))
            .execute();
    }

    public List<Link> findUnUpdatedLinks() {
        return dslContext.resultQuery("select * from LINK where EXTRACT(SECOND FROM (now() - last_check_time )) > 30")
            .fetchInto(Link.class); //TODO refactor?
    }

    public void updateLinkLastCheckTimeById(Long id, Timestamp timestamp) {
        dslContext.update(LINK)
            .set(LINK.LAST_CHECK_TIME, offsetDateTimeFromTimestamp(timestamp))
            .where(LINK.ID.eq(id))
            .returning(LINK)
            .fetchOne();
    }

    public LinkSof getLinkPropertiesById(Long id) {
        return dslContext.selectFrom(SOF_LINK_PROPERTIES)
            .where(SOF_LINK_PROPERTIES.LINK_ID.eq(id))
            .fetchOneInto(LinkSof.class);
    }

    public void updateCountOfCommentsById(Long id, Long count) {
        dslContext.update(SOF_LINK_PROPERTIES)
            .set(SOF_LINK_PROPERTIES.COMMENT_COUNT, count)
            .where(SOF_LINK_PROPERTIES.LINK_ID.eq(id))
            .returning(SOF_LINK_PROPERTIES)
            .fetchOne();
    }

    public void updateCountOfAnswersById(Long id, Long count) {
        dslContext.update(SOF_LINK_PROPERTIES)
            .set(SOF_LINK_PROPERTIES.ANSWER_COUNT, count)
            .where(SOF_LINK_PROPERTIES.LINK_ID.eq(id))
            .returning(SOF_LINK_PROPERTIES)
            .fetchOne();
    }

    private OffsetDateTime offsetDateTimeFromTimestamp(Timestamp timestamp) {
        return OffsetDateTime.ofInstant(timestamp.toInstant(), ZoneId.of("UTC"));
    }

}
