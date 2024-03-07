package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.processor.CommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramBotConfig {
    /**
     * Method of creating a bot telegram bin using the pengrad SDK.
     *
     * @param appConfig      bean configuration application with the necessary parameters for creating a bot.
     * @param commandHandler bean with commands for bot.
     */
    @Bean
    TelegramBot telegramBot(ApplicationConfig appConfig, CommandHandler commandHandler) {
        var bot = new TelegramBot(appConfig.telegramToken());

        bot.execute(createMenuCommand(commandHandler));
        return bot;
    }

    /**
     * Method of preparing the menu with commands for the bot.
     */
    private SetMyCommands createMenuCommand(CommandHandler commandHandler) {
        return new SetMyCommands(commandHandler.commandMap().values().stream().map(command -> new BotCommand(
            command.command(),
            command.description()
        )).toArray(BotCommand[]::new));
    }
}
