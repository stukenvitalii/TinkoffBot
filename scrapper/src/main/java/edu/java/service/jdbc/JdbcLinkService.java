package edu.java.service.jdbc;

import edu.java.model.dto.Link;
import edu.java.repository.LinkRepository;
import edu.java.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.List;


@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {

    private final LinkRepository linkRepository;

    @Override
    public List<Link> getLinks() {
        return linkRepository.findAll();
    }

    @Override
    public void addLink(Link link) {
        linkRepository.add(link);
    }

    @Override
    public void removeLink(Long id) {
        linkRepository.remove(id);
    }

    @Override
    public void updateLinkLastCheckTime(Long id, Timestamp lastCheckTime) {
        linkRepository.updateLinkLastCheckTimeById(id, lastCheckTime);
    }

    @Override
    public List<Link> getUnUpdatedLinks() {
        return linkRepository.getUnUpdatedLinks();
    }
}
