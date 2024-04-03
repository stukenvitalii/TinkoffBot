package edu.java.service.sender;

import edu.java.model.request.LinkUpdateRequest;
import org.springframework.stereotype.Service;

@Service
public interface SenderService {
    void updateLink(LinkUpdateRequest linkUpdateRequest);
}
