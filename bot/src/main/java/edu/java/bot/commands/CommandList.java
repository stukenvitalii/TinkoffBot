package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.repository.UserService;
import edu.java.bot.users.User;
import java.net.URI;

public class CommandList implements Command {
    private final UserService userService;

    private final String unknownUser = "Пользователь не существует. Зарегистрируйтесь с помощью команды /start";
    private final String emptyList = "Вы не отслеживаете ни одной ссылки";

    public CommandList(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "Позволяет получить список отслеживаемых ссылок.";
    }

    @Override
    public String handle(Update update) {
//        ScrapperClient scrapperClient = new ScrapperClient(WebClient.builder().build());
//        System.out.println(scrapperClient.getLinksById(10L));
        long chatId = update.message().chat().id();
        return getTrackingLinks(chatId);
    }

    public String getTrackingLinks(long chatId) {

        var userOptional = userService.findUserById(chatId);
        StringBuilder str = new StringBuilder();

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            var listOfSites = user.getSites();
            if (listOfSites.isEmpty()) {
                return emptyList;
            }
            int i = 1;
            for (URI site: listOfSites) {
                str.append(i++).append(") ").append(site.toString()).append("\n");
            }
            return str.toString();
        }
        return unknownUser;

    }


}
