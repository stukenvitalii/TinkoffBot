package edu.java.bot.service;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.model.SessionState;
import edu.java.bot.processor.CommandHandler;
import edu.java.bot.repository.UserService;
import edu.java.bot.url_processor.UrlProcessor;
import edu.java.bot.users.User;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    public static final String DO_REGISTRATION_MESSAGE = "Необходимо зарегистрироваться.";
    public static final String INVALID_URI_MESSAGE = "Неверно указан URI.";
    public static final String INVALID_COMMAND_MESSAGE = "Команда введена некорректно.";
    public static final String SUCCESS_TRACK_SITE_MESSAGE = "Сайт успешно добавлен в отслеживаемые.";
    public static final String DUPLICATE_TRACKING_MESSAGE = "Этот сайт уже отслеживается.";
    public static final String INVALID_FOR_TRACK_SITE_MESSAGE = "Отслеживание ресурса с этого сайта не поддерживается.";

    private final CommandHandler commandHandler;
    private final UserService userRepository;
    private final UrlProcessor urlProcessor;

    public MessageService(
        CommandHandler commandHandler,
        UserService userRepository,
        UrlProcessor urlProcessor
    ) {
        this.commandHandler = commandHandler;
        this.userRepository = userRepository;
        this.urlProcessor = urlProcessor;
    }

    public String prepareResponseMessage(Update update) {
        var chatId = update.message().chat().id();
        var textMessage = update.message().text();
        var botCommand = commandHandler.getCommand(textMessage);
        return botCommand.map(command -> command.handle(update))
            .orElseGet(() -> processNonCommandMessage(chatId, textMessage));
    }

    private String processNonCommandMessage(Long chatId, String text) {
        var userOptional = userRepository.findUserById(chatId);
        if (userOptional.isEmpty()) {
            return DO_REGISTRATION_MESSAGE;
        }

        var user = userOptional.get();
        try {
            if (!user.getState().equals(SessionState.BASE_STATE)) {
                URI uri = new URI(text);
                String host = uri.getHost();
                if (host == null) {
                    return INVALID_URI_MESSAGE;
                }
                if (urlProcessor.isValidUrl(uri)) {
                    return processStateUserMessage(user, uri);
                }
                return INVALID_FOR_TRACK_SITE_MESSAGE; //TODO исправить правильные, но неподдерживаемые сайты
            }
            else {
                return INVALID_COMMAND_MESSAGE;
            }

        } catch (URISyntaxException e) {
            return INVALID_URI_MESSAGE;
        }
    }

    private String processStateUserMessage(User user, URI uri) {
        if (user.getState().equals(SessionState.WAIT_URI_FOR_TRACKING)) {
            return prepareWaitTrackingMessage(user, uri);
        }

        if (user.getState().equals(SessionState.WAIT_URI_FOR_UNTRACKING)) {
            return prepareWaitUnTrackingMessage(user, uri);
        }
        return INVALID_COMMAND_MESSAGE;
    }

    private String prepareWaitTrackingMessage(User user, URI uri) {
        if (urlProcessor.isValidUrl(uri)) {
            return (updateUserTrackingSites(user, uri)) ? SUCCESS_TRACK_SITE_MESSAGE
                : DUPLICATE_TRACKING_MESSAGE;
        }
        return INVALID_FOR_TRACK_SITE_MESSAGE;
    }

    private String prepareWaitUnTrackingMessage(User user, URI uri) {
        if (urlProcessor.isValidUrl(uri)) {
            return (deleteTrackingSites(user, uri)) ? "Ресурс более не отслеживается."
                : "Вы не отслеживали данный ресурс.";
        }
        return INVALID_FOR_TRACK_SITE_MESSAGE;
    }

    private boolean updateUserTrackingSites(User user, URI uri) {
        List<URI> trackSites = new ArrayList<>(user.getSites());
        if (trackSites.contains(uri)) {
            return false;
        }

        trackSites.add(uri);
        updateTrackSitesAndCommit(user, trackSites);
        return true;
    }

    private boolean deleteTrackingSites(User user, URI uri) {
        List<URI> trackSites = new ArrayList<>(user.getSites());
        if (!trackSites.contains(uri)) {
            return false;
        }

        trackSites.remove(uri);
        updateTrackSitesAndCommit(user, trackSites);
        return true;
    }

    private void updateTrackSitesAndCommit(User user, List<URI> trackSites) {
        user.setSites(trackSites);
        user.setState(SessionState.BASE_STATE);
        userRepository.saveUser(user);
    }

}
