package edu.java.service;

import edu.java.model.dto.Link;
import edu.java.model.dto.LinkSof;
import java.sql.Timestamp;
import java.util.List;

public interface LinkService {
    List<Link> getLinks();

    void addLink(Link link);

    void removeLink(Long id);

    List<Link> getUnUpdatedLinks();

    void updateLinkLastCheckTimeById(Long id, Timestamp lastCheckTime);

    LinkSof getLinkPropertiesById(Long id);

    void updateCountOfCommentsById(Long id, Long count);

    void updateCountOfAnswersById(Long id, Long count);
}
