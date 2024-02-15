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
        if (commandList.isEmpty()) {
            return "Список команд пуст.";
        }
        StringBuilder message = new StringBuilder();

        for (int i = 0;i < commandList.size();i++) {
            message.append(commandList.get(i).command()).append(" - ").append(commandList.get(i).description()).append("\n");
        }
        return message.toString();
    }
}
