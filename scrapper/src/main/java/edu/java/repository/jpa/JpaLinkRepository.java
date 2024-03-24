package edu.java.repository.jpa;

import edu.java.model.dto.Link;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    List<Link> findAll();

    @Modifying
    @Query("SELECT l FROM Link l WHERE (CURRENT_TIMESTAMP() - l.lastCheckTime) > 30000")
    List<Link> findOldLinks();

    Link save(Link link);

    void removeLinkById(Long id);

    @Modifying
    @Query("update Link set  lastCheckTime = :lastCheckTime where id = :linkId")
    void updateLinkLastCheckTimeById(Timestamp lastCheckTime, Long linkId);

    @Modifying
    @Query("update LinkSof set countOfComments = :count where linkId = :linkId")
    void updateCountOfCommentsById(Long linkId, Long count);

    @Modifying
    @Query("update LinkSof set countOfAnswer = :count where linkId = :linkId")
    void updateCountOfAnswersById(Long linkId, Long count);


}
