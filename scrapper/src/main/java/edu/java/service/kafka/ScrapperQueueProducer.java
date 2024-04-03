package edu.java.service.kafka;

import edu.java.model.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ScrapperQueueProducer {
    private final NewTopic topic;

    private final KafkaTemplate<String, String> template;

    public ScrapperQueueProducer(NewTopic topic, KafkaTemplate<String, String> template) {
        this.topic = topic;
        this.template = template;
    }

    public void send() {
        template.send(topic.name(), "scrapper queue producer");
    }
}
