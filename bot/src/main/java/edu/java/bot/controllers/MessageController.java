package edu.java.bot.controllers;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import edu.java.bot.service.MessageService;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController implements UpdatesListener {
    private static final Logger MESSAGE_LOGGER = LogManager.getLogger(MessageController.class.getName());
    private final TelegramBot telegramBot;
    private final MessageService messageService;

    public MessageController(TelegramBot telegramBot, MessageService messageService) {
        telegramBot.setUpdatesListener(this);

        this.telegramBot = telegramBot;
        this.messageService = messageService;
    }

    @Override
    public int process(List<Update> list) {
        if (!list.isEmpty()) {
            list.forEach(update -> {
                    if (update != null) {
                        var message = new SendMessage(
                            update.message().chat().id(),
                            messageService.prepareResponseMessage(update)
                        );

                        telegramBot.execute(
                            message,
                            new Callback<SendMessage, SendResponse>() {
                                @Override
                                public void onResponse(SendMessage request, SendResponse response) {
                                    MESSAGE_LOGGER.info("Отправка ответа %s на запрос  %s".formatted(
                                        request.toString(),
                                        response.message().text()
                                    ));
                                }

                                @Override
                                public void onFailure(SendMessage request, IOException e) {
                                    MESSAGE_LOGGER.error("Ошибка выполнения запроса: " + e.getMessage());

                                }
                            }
                        );
                    }
                }
            );
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
