package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.repository.UserService;
import edu.java.bot.users.User;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CommandTrackTest {
    @Mock
    UserService userService;

    @InjectMocks
    CommandTrack commandTrack;

    private final String trackMessage = "Предоставьте ссылку на ресурс. ";
    private final String unknownUser = "Пользователь не существует. Зарегистрируйтесь с помощью команды /start.";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void command() {
        assertEquals("/track",commandTrack.command());
    }

    @Test
    void description() {
        assertEquals("Позволяет добавить ссылку в список отслеживаемых.", commandTrack.description());
    }

    @Test
    void handleRegisteredUser() {
        long chatId = 123456L;
        Update update = getUpdate(chatId);
        Optional<User> optionalMockUser = mock(Optional.class);
        User mockUser = mock(User.class);
        when(optionalMockUser.isPresent()).thenReturn(true);
        when(optionalMockUser.get()).thenReturn(mockUser);
        when(userService.findUserById(chatId)).thenReturn(optionalMockUser);

        assertEquals(trackMessage, commandTrack.handle(update));

        //TODO дописать verify на кол-во вызовов
    }

    @Test
    public void handleNotRegisteredUser() {
        long chatId = 123456L;
        Update update = getUpdate(chatId);

        when(userService.findUserById(anyLong())).thenReturn(Optional.empty());

        String result = commandTrack.handle(update);
        assertEquals(unknownUser, result);

        verify(userService, times(1)).findUserById(chatId);
    }

    @NotNull
    private static Update getUpdate(long chatId) {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(chatId);
        return update;
    }
}
