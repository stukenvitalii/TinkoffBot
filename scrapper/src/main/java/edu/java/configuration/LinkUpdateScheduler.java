package edu.java.configuration;

import org.jboss.logging.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class LinkUpdateScheduler {
    private final Logger logger = Logger.getLogger(LinkUpdateScheduler.class.getName());

@Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        logger.info("I'm updating!");
    }
}
