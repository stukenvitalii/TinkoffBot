package edu.java.service.kafka;

import dto.request.LinkUpdateRequest;
import edu.java.service.sender.SenderService;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "app.use-queue", havingValue = "true", matchIfMissing = true)
public class ScrapperQueueProducer implements SenderService {
    private final NewTopic topic;

    private final KafkaTemplate<String, LinkUpdateRequest> template;

    public ScrapperQueueProducer(NewTopic topic, KafkaTemplate<String, LinkUpdateRequest> template) {
        this.topic = topic;
        this.template = template;
    }

    @Override
    public void updateLink(LinkUpdateRequest linkUpdateRequest) {
        template.send(topic.name(), linkUpdateRequest);
    }
}
