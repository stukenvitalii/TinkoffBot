package edu.java.bot.commands;

import edu.java.bot.repository.UserService;
import edu.java.bot.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommandListTest {

    private final String unknownUser = "Пользователь не существует. Зарегистрируйтесь с помощью команды /start";
    private final String emptyList = "Вы не отслеживаете ни одной ссылки";

    private CommandList commandList;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService();
        commandList = new CommandList(userService);
    }

    @Test
    void command() {
        assertEquals("/list", commandList.command());
    }

    @Test
    void description() {
        assertEquals("Позволяет получить список отслеживаемых ссылок.", commandList.description());
    }

    @Test
    void getTrackingLinksNotRegistered() {
        assertEquals(unknownUser, commandList.getTrackingLinks(-1L));
    }

    @Test
    void getTrackingLinksEmptyList() {
        User mockUser = mock(User.class);
        userService.saveUser(mockUser);
        assertEquals(emptyList, commandList.getTrackingLinks(mockUser.getId()));
    }

    @Test
    void getTrackingLinksNormal() throws URISyntaxException {
        User mockUser = mock(User.class);

        List<URI> sitesList = new ArrayList<>();
        sitesList.add(new URI("https://github.com"));

        when(mockUser.getSites()).thenReturn(sitesList);

        when(mockUser.getId()).thenReturn(10L);
        userService.saveUser(mockUser);

        assertEquals("1) https://github.com\n", commandList.getTrackingLinks(mockUser.getId()));
    }

}
