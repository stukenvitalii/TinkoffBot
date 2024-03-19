package edu.java.repository.jpa;

import edu.java.model.dto.Link;
import edu.java.model.dto.LinkSof;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.List;

public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    List<Link> findAll();

//    List<Link> findOldLinks

    //void save(Link link);

    @Transactional
    void removeLinkById(Long id);

    @Modifying
    @Query("update Link set  lastCheckTime = (:lastCheckTime) where id = :linkId")
    void updateLinkLastCheckTimeById(Timestamp lastCheckTime, Long linkId);

    @Modifying
    @Query("update LinkSof set countOfComments = :count where linkId = :linkId")
    void updateCountOfCommentsById(Long linkId, Long count);

    @Modifying
    @Query("update LinkSof set countOfAnswer = :count where linkId = :linkId")
    void updateCountOfAnswersById(Long linkId, Long count);


}
