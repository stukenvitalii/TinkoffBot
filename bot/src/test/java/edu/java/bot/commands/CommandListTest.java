package edu.java.bot.commands;

import edu.java.bot.repository.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandListTest {

    private CommandList commandList;
    private UserService userService;
    @BeforeEach
    public void setUp() {
        userService = new UserService();
        commandList = new CommandList(userService);
    }
    @Test
    void command() {
        assertEquals(commandList.command(),"/list");
    }

    @Test
    void description() {
        assertEquals(commandList.description(),"Позволяет получить список отслеживаемых ссылок.");
    }

    @Test
    void handle() {
    }

    @Test
    void getTrackingLinks() {
    }
}
