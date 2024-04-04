package edu.java.bot.configuration.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {
    @Value("${app.kafka.topics}")
    String topics;

    @Value("${app.kafka.partitions-num}")
    int partitionsNum;

    @Value("${app.kafka.replicas-num}")
    int replicasNum;

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(topics)
            .partitions(partitionsNum)
            .replicas(replicasNum)
            .build();
    }
}
