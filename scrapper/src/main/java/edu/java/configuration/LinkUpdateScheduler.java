package edu.java.configuration;

import edu.java.client.BotClient;
import edu.java.github.GitHubClient;
import edu.java.github.GitHubRepository;
import edu.java.model.dto.Link;
import edu.java.service.jdbc.JdbcLinkService;
import edu.java.stackoverflow.StackOverFlowClient;
import edu.java.stackoverflow.StackOverFlowQuestion;
import edu.java.stackoverflow.StackOverFlowResponse;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
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

    private final BotClient botClient = new BotClient(WebClient.builder().build());

    @Scheduled(fixedDelayString = "#{scheduler.interval}")
    public void update() throws InterruptedException {
        Thread.sleep(10000); //TODO remove
        logger.info("I'm updating!");

        updateOldLinks();
    }

    private void updateOldLinks() {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        for (Link link: jdbcLinkService.getLinks()) {
            if ( now.getTime()/1000 - link.getLastCheckTime().getTime()/1000  > 30 ) {

                if (link.getUrl().getHost().equals("github.com")) {
                    List<String> fragments = List.of(link.getUrl().toString().split("/"));
                    GitHubRepository rep = gitHubClient.getRepositoryInfo(fragments.get(3), fragments.get(4)).block();
                    Timestamp lastPush = rep.getLastPush();

                    if (lastPush.after(link.getLastCheckTime())) {
                        link.setLastCheckTime(Timestamp.valueOf(LocalDateTime.now())); //TODO update to DB

                        botClient.updateLink(link.getUrl(), List.of(link.getChatId()));

                        System.out.println("rep" + lastPush + rep.getName());
                    }
                }
                else if (link.getUrl().getHost().equals("stackoverflow.com")) {
                    List<String> fragments = List.of(link.getUrl().toString().split("/"));
                    StackOverFlowQuestion question = stackOverFlowClient.fetchQuestion(Long.parseLong(fragments.get(4))).block().getItems().getFirst();
                    Timestamp lastActivity = question.getLastActivityAsTimestamp();

                    if (lastActivity.after(link.getLastCheckTime())) {
                        link.setLastCheckTime(Timestamp.valueOf(LocalDateTime.now())); //TODO update to DB
                        System.out.println("sof " + lastActivity + question.getTitle());
                    }
                }

            }
        }
    }


}
