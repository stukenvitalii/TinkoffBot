package edu.java.bot.service.kafka;

import edu.java.bot.model.request.LinkUpdateRequest;
import edu.java.bot.service.MessageServiceInterface;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BotQueueConsumer {
    private final KafkaTemplate<String, String> kafkaTemplate; //TODO change to Link...

    private final MessageServiceInterface messageServiceInterface;

    public BotQueueConsumer(KafkaTemplate<String, String> kafkaTemplate, MessageServiceInterface messageServiceInterface) {
        this.kafkaTemplate = kafkaTemplate;
        this.messageServiceInterface = messageServiceInterface;
    }

    @KafkaListener(id = "myId", topics = "topic1")
    public void listen(String linkUpdateRequest) {
        messageServiceInterface.sendNotification(linkUpdateRequest);
    }
}
