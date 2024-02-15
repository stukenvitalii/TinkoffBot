package edu.java.bot.commands;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandHelpTest {

    @Mock
    private static List<Command> mockCommandList;

    @InjectMocks
    private CommandHelp commandHelp;

    @Test
    void command() {
        assertEquals("/help",commandHelp.command());
    }

    @Test
    void description() {
        assertEquals("Выводит доступные команды.", commandHelp.description());
    }

    @Test
    public void testHandle_withEmptyList() {
        when(mockCommandList.isEmpty()).thenReturn(true);

        String result = commandHelp.handle(null);

        assertEquals("Список команд пуст.", result);
    }

    @Test
    public void testHandle_withMultipleCommands() {
        Command mockCommand1 = mock(Command.class);
        when(mockCommand1.command()).thenReturn("/track");
        when(mockCommand1.description()).thenReturn("Позволяет добавить ссылку в список отслеживаемых.");

        Command mockCommand2 = mock(Command.class);
        when(mockCommand2.command()).thenReturn("/untrack");
        when(mockCommand2.description()).thenReturn("Позволяет убрать ссылку из списка отслеживаемых.");

        List<Command> mockList = new ArrayList<>();
        mockList.add(mockCommand1);
        mockList.add(mockCommand2);

        when(mockCommandList.isEmpty()).thenReturn(false);
        when(mockCommandList.size()).thenReturn(mockList.size());
        when(mockCommandList.get(0)).thenReturn(mockList.get(0));
        when(mockCommandList.get(1)).thenReturn(mockList.get(1));

        String result = commandHelp.handle(null);

        String expectedResult = "/track - Позволяет добавить ссылку в список отслеживаемых.\n/untrack - Позволяет убрать ссылку из списка отслеживаемых.\n";
        assertEquals(expectedResult, result);
    }
}
