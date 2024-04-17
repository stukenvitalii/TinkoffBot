package edu.java.service.jooq;

import edu.java.model.dto.Link;
import edu.java.model.dto.LinkSof;
import edu.java.repository.jooq.JooqLinkRepository;
import edu.java.service.LinkService;
import java.sql.Timestamp;
import java.util.List;

public class JooqLinkService implements LinkService {
    private final JooqLinkRepository jooqLinkRepository;

    public JooqLinkService(JooqLinkRepository jooqLinkRepository) {
        this.jooqLinkRepository = jooqLinkRepository;
    }

    @Override
    public List<Link> getLinks() {
        return jooqLinkRepository.findAll();
    }

    @Override
    public void addLink(Link link) {
        jooqLinkRepository.add(link);
    }

    @Override
    public void removeLink(Long id) {
        jooqLinkRepository.remove(id);
    }

    @Override
    public List<Link> getUnUpdatedLinks() {
        return jooqLinkRepository.findUnUpdatedLinks();
    }

    @Override
    public void updateLinkLastCheckTimeById(Long id, Timestamp lastCheckTime) {
        jooqLinkRepository.updateLinkLastCheckTimeById(id, lastCheckTime);
    }

    @Override
    public LinkSof getLinkPropertiesById(Long id) {
        return jooqLinkRepository.getLinkPropertiesById(id);
    }

    @Override
    public void updateCountOfCommentsById(Long id, Long count) {
        jooqLinkRepository.updateCountOfCommentsById(id, count);
    }

    @Override
    public void updateCountOfAnswersById(Long id, Long count) {
        jooqLinkRepository.updateCountOfAnswersById(id, count);
    }
}
