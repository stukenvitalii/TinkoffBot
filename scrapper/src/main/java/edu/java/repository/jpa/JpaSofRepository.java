package edu.java.repository.jpa;

import edu.java.model.dto.LinkSof;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSofRepository extends JpaRepository<LinkSof, Long> {
    LinkSof getLinkSofByLinkId(Long id);
}
