package edu.java.service;

import edu.java.model.dto.Link;
import java.util.List;

public interface LinkService {
    List<Link> getLinks();

    void addLink(Link link);

    void removeLink(Long id);
}
