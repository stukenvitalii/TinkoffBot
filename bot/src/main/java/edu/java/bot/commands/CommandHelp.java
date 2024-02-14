package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import java.util.List;

public class CommandHelp implements Command {
    private final List<Command> commandList;

    public CommandHelp(List<Command> commandList) {
        this.commandList = commandList;
    }

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "Выводит доступные команды.";
    }

    @Override
    public String handle(Update update) {
        StringBuilder message = new StringBuilder();
        for (Command command: commandList) {
            message.append(command.command()).append(" - ").append(command.description()).append("\n");
        }
        return message.toString();
    }
}
