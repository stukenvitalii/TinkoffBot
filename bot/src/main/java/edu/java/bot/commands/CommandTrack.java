package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.model.SessionState;
import edu.java.bot.repository.UserService;
import edu.java.bot.users.User;

public class CommandTrack implements Command {

    private final String trackMessage = "Предоставьте ссылку на ресурс. ";
    private final String unknownUser = "Пользователь не существует. Зарегистрируйтесь с помощью команды /start.";
    private final UserService userService;

    public CommandTrack(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "Позволяет добавить ссылку в список отслеживаемых.";
    }

    @Override
    public String handle(Update update) {
        var chatId = update.message().chat().id();

        return sendTrackMessage(chatId);
    }


    private String sendTrackMessage(Long chatId) {
        var userOptional = userService.findUserById(chatId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            changeStateAndSaveUser(user);

            return trackMessage;
        }
        return unknownUser;
    }

    private void changeStateAndSaveUser(User user) {
        user.setState(SessionState.WAIT_URI_FOR_TRACKING);
        userService.saveUser(user);
    }


}
