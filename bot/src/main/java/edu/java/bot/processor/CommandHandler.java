package edu.java.bot.processor;

import edu.java.bot.commands.Command;
import java.util.Map;
import java.util.Optional;

public record CommandHandler(Map<String, Command> commandMap) {
    public Optional<Command> getCommand(String c) {
        return (commandMap.containsKey(c)) ? Optional.of(commandMap.get(c)) : Optional.empty();
    }
}
