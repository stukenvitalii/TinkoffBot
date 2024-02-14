package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.model.SessionState;
import edu.java.bot.repository.UserService;
import edu.java.bot.users.User;

public class CommandUntrack implements Command {
    private final UserService userService;
    private final String unknownUser = "Пользователь не существует. Зарегистрируйтесь с помощью команды /start.";
    private final String untrackMessage = "Предоставьте ссылку на ресурс.";

    public CommandUntrack(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "Позволяет убрать ссылку из списка отслеживаемых.";
    }

    @Override
    public String handle(Update update) {
        long chatId = update.message().chat().id();
        return prepareUntrackMessage(chatId);
    }

    private String prepareUntrackMessage(long chatId) {
        var userOptional = userService.findUserById(chatId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            changeStateAndSaveUser(user);
            return untrackMessage;
        }
        return unknownUser;
    }

    private void changeStateAndSaveUser(User user) {
        user.setState(SessionState.WAIT_URI_FOR_UNTRACKING);
        userService.saveUser(user);
    }
}
