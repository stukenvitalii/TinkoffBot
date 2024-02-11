package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;

public interface Command {
    String command();

    String description();
    /**
     * Method that executes the command action.
     *
     * @param update the update that came to the telegram bot.
     * @return response message to the executed command.
     */

    String handle(Update update);

}
