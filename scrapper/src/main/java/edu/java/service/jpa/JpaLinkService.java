package edu.java.service.jpa;

import edu.java.model.dto.Link;
import edu.java.model.dto.LinkSof;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.repository.jpa.JpaSofRepository;
import edu.java.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.Timestamp;
import java.util.List;

public class JpaLinkService implements LinkService {

    private final JpaLinkRepository jpaLinkRepository;
    private final JpaSofRepository jpaSofRepository;

    public JpaLinkService(JpaLinkRepository jpaLinkRepository, JpaSofRepository jpaSofRepository) {
        this.jpaLinkRepository = jpaLinkRepository;
        this.jpaSofRepository = jpaSofRepository;
    }

    @Override
    public List<Link> getLinks() {
        return jpaLinkRepository.findAll();
    }

    @Override
    public void addLink(Link link) {
        jpaLinkRepository.save(link);
    }

    @Override
    public void removeLink(Long id) {
        jpaLinkRepository.removeLinkById(id);
    }

    @Override
    public List<Link> getUnUpdatedLinks() {
        return jpaLinkRepository.findOldLinks();
    }

    @Override
    public void updateLinkLastCheckTimeById(Long id, Timestamp lastCheckTime) {
        jpaLinkRepository.updateLinkLastCheckTimeById(lastCheckTime, id);
    }

    @Override
    public LinkSof getLinkPropertiesById(Long id) {
        return jpaSofRepository.getLinkSofByLinkId(id);
    }

    @Override
    public void updateCountOfCommentsById(Long id, Long count) {
        jpaLinkRepository.updateCountOfCommentsById(id, count);
    }

    @Override
    public void updateCountOfAnswersById(Long id, Long count) {
        jpaLinkRepository.updateCountOfAnswersById(id, count);
    }
}
