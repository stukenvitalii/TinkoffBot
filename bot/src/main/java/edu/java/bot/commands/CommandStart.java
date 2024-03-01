package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.model.BotClient;
import edu.java.bot.model.SessionState;
import edu.java.bot.repository.UserService;
import edu.java.bot.users.User;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CommandStart implements Command {
    public static final String SUCCESS_REGISTRATION_MESSAGE = "Регистрация прошла успешно!";
    private static final String ALREADY_REGISTRATED_MESSAGE = "Вы уже зарегистрированы в боте!";
    private final UserService userService;

    public CommandStart(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Позволяет зарегистрироваться в нашей системе.";
    }

    @Override
    public String handle(Update update) {
        long chatId = update.message().chat().id();

        return registerUser(chatId);
    }

    public String registerUser(long chatId) throws IllegalStateException{
        var userOptional = userService.findUserById(chatId);

        if (userOptional.isEmpty()) {
            User user = new User(chatId, List.of(), SessionState.BASE_STATE);

            userService.saveUser(user);
            System.out.println(new BotClient(WebClient.builder().build()).addChatById(user.getId().toString()));
            return SUCCESS_REGISTRATION_MESSAGE;
        }
        throw new IllegalStateException(ALREADY_REGISTRATED_MESSAGE);
    }
}
