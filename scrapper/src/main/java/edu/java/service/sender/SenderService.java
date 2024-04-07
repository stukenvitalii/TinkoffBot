package edu.java.service.sender;

import dto.request.LinkUpdateRequest;
import org.springframework.stereotype.Service;

@Service
public interface SenderService {
    void updateLink(LinkUpdateRequest linkUpdateRequest);
}
