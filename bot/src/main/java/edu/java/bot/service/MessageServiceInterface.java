package edu.java.bot.service;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.model.request.LinkUpdateRequest;
import edu.java.bot.users.User;
import java.net.URI;
import java.util.List;

public interface MessageServiceInterface {
    String prepareResponseMessage(Update update);

    private String processNonCommandMessage(Long chatId, String text) {
        return null;
    }

    private String processStateUserMessage(User user, URI uri) {
        return null;
    }

    private String prepareWaitTrackingMessage(User user, URI uri) {
        return null;
    }

    private String prepareWaitUnTrackingMessage(User user, URI uri) {
        return null;
    }

    private void updateTrackSitesAndCommit(User user, List<URI> trackSites) {
    }
    void sendNotification(LinkUpdateRequest linkUpdateRequest) ;
}
