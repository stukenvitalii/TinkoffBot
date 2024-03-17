package edu.java;

import edu.java.configuration.ApplicationConfig;
import edu.java.model.dto.Link;
import edu.java.repository.JooqLinkRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class ScrapperApplication {
    public static void main(String[] args) throws URISyntaxException {
        var jooqRepository =  SpringApplication.run(ScrapperApplication.class, args).getBeanFactory().getBean("jooqLinkRepository");
        demo((JooqLinkRepository) jooqRepository);
    }

    public static void demo(JooqLinkRepository jooqLinkRepository) throws URISyntaxException {
//        Link link = new Link();
//        link.setUrl(new URI("https://vk.com"));
//        link.setChatId(644124159);
//        link.setLastCheckTime(new Timestamp(3094830));
//        link.setCreatedAt(new Timestamp(5345345));

        for (Link link : jooqLinkRepository.findUnUpdatedLinks()) {
            System.out.println(link.getUrl());
        }
    }
}
