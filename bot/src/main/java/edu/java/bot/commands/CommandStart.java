package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.model.SessionState;
import edu.java.bot.model.exception.ApiException;
import edu.java.bot.repository.UserService;
import edu.java.bot.users.User;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommandStart implements Command {
    public static final String SUCCESS_REGISTRATION_MESSAGE = "Регистрация прошла успешно!";
    private static final String ALREADY_REGISTRATED_MESSAGE = "Вы уже зарегистрированы в боте!";
    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandStart.class);

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

        try {
            return registerUser(chatId);
        } catch (ApiException ex) {
            LOGGER.error(ex.getMessage());
            return ex.getMessage();
        }
    }

    public String registerUser(long chatId) throws ApiException {
        var userOptional = userService.findUserById(chatId);

        if (userOptional.isEmpty()) {
            User user = new User(chatId, List.of(), SessionState.BASE_STATE);

            userService.saveUser(user);
//            System.out.println(new ScrapperClient(WebClient.builder().build()).addChatById(user.getId()));

            return SUCCESS_REGISTRATION_MESSAGE;
        }
        return ALREADY_REGISTRATED_MESSAGE;
    }
}
