package edu.java.service.jdbc;

import edu.java.model.dto.Link;
import edu.java.repository.LinkRepository;
import edu.java.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
    public int addLink(Link link) {
        return linkRepository.add(link);
    }

    @Override
    public int removeLink(Long id) {
        return linkRepository.remove(id);
    }

//    @Override
//    public List<Link> getUnUpdatedLinks() {
//        return linkRepository.findUnUpdatedLinks();
//    }
}
