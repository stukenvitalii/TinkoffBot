package edu.java.service.jdbc;

import edu.java.model.dto.Link;
import edu.java.model.dto.LinkSof;
import edu.java.repository.jdbc.JdbcLinkRepository;
import edu.java.service.LinkService;
import java.sql.Timestamp;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {

    private final JdbcLinkRepository jdbcLinkRepository;

    @Override
    public List<Link> getLinks() {
        return jdbcLinkRepository.findAll();
    }

    @Override
    public void addLink(Link link) {
        jdbcLinkRepository.add(link);
    }

    @Override
    public void removeLink(Long id) {
        jdbcLinkRepository.remove(id);
    }

    @Override
    public List<Link> getUnUpdatedLinks() {
        return jdbcLinkRepository.findUnUpdatedLinks();
    }

    @Override
    public void updateLinkLastCheckTimeById(Long id, Timestamp lastCheckTime) {
        jdbcLinkRepository.updateLinkLastCheckTimeById(id, lastCheckTime);
    }

    @Override
    public LinkSof getLinkPropertiesById(Long id) {
        return jdbcLinkRepository.getLinkPropertiesById(id);
    }

    @Override
    public void updateCountOfCommentsById(Long id, Long count) {
        jdbcLinkRepository.updateCountOfCommentsById(id, count);
    }

    @Override
    public void updateCountOfAnswersById(Long id, Long count) {
        jdbcLinkRepository.updateCountOfAnswersById(id, count);
    }
}
