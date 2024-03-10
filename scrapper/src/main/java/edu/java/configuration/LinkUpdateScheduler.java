package edu.java.configuration;

import edu.java.github.GitHubClient;
import edu.java.model.dto.Link;
import edu.java.service.jdbc.JdbcLinkService;
import edu.java.stackoverflow.StackOverFlowClient;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableScheduling
@ConditionalOnProperty(value = "app.scheduler.enable", havingValue = "true", matchIfMissing = true)
public class LinkUpdateScheduler {
    private final Logger logger = Logger.getLogger(LinkUpdateScheduler.class.getName());
    private final JdbcLinkService jdbcLinkService;



    public LinkUpdateScheduler(JdbcLinkService jdbcLinkService) {
        this.jdbcLinkService = jdbcLinkService;

    }
    @Autowired
    private GitHubClient gitHubClient;

    @Autowired
    private StackOverFlowClient stackOverFlowClient;

    @Scheduled(fixedDelayString = "#{scheduler.interval}")
    public void update() {
        logger.info("I'm updating!");

        getLinkListUnUpdated(); //TODO rename
    }

    private void getLinkListUnUpdated() { //TODO rename
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        for (Link link: jdbcLinkService.getLinks()) {
            if ( now.getTime()/1000 - link.getLastCheckTime().getTime()/1000  > 30 ) {
                if (link.getUrl().getHost().equals("github.com")) {
                    List<String> fragments = List.of(link.getUrl().toString().split("/"));
                    gitHubClient.getRepositoryInfo(fragments.get(3), fragments.get(4));
                }
                else if (link.getUrl().getHost().equals("api.stackexchange.com")) {
                    List<String> fragments = List.of(link.getUrl().toString().split("/"));
                    stackOverFlowClient.fetchQuestion(Long.parseLong(fragments.get(4)));
                }
            }
        }
    }


}
