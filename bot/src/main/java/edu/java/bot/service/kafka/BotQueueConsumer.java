package edu.java.bot.service.kafka;

import dto.request.LinkUpdateRequest;
import edu.java.bot.service.MessageServiceInterface;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BotQueueConsumer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final MessageServiceInterface messageServiceInterface;

    public BotQueueConsumer(
        KafkaTemplate<String, String> kafkaTemplate,
        MessageServiceInterface messageServiceInterface
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.messageServiceInterface = messageServiceInterface;
    }

    @KafkaListener(id = "myId", topics = "topic1")
    public void listen(LinkUpdateRequest linkUpdateRequest) {
        if (checkLinkUpdateRequest(linkUpdateRequest)) {
            messageServiceInterface.sendNotification(linkUpdateRequest);
        } else {
            kafkaTemplate.send("topic_dlq", linkUpdateRequest.toString());
        }
    }

    private boolean checkLinkUpdateRequest(LinkUpdateRequest body) {
        return body.getDescription() != null
            && !body.getTgChatIds().isEmpty()
            && body.getUrl() != null
            && body.getId() != null;
    }
}
