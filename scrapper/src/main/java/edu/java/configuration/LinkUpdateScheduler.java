package edu.java.configuration;

import edu.java.client.BotClient;
import edu.java.github.GitHubClient;
import edu.java.github.GitHubRepository;
import edu.java.model.dto.Link;
import edu.java.service.jdbc.JdbcLinkService;
import edu.java.stackoverflow.StackOverFlowClient;
import edu.java.stackoverflow.StackOverFlowQuestion;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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
    public void update() {
        logger.info("I'm updating!");
        updateOldLinks();
    }

    private void updateOldLinks() {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        for (Link link : jdbcLinkService.getUnUpdatedLinks()) {
            if (link.getUrl().getHost().equals("github.com")) {
                updateGithubLink(link, now);
            } else if (link.getUrl().getHost().equals("stackoverflow.com")) {
                updateStackOverFlowLink(link, now);
            }
        }
    }

    private void updateStackOverFlowLink(Link link, Timestamp now) {

        String path = link.getUrl().getPath();
        Pattern pattern = Pattern.compile("/questions/(?<id>\\d+)");
        Matcher matcher = pattern.matcher(path);

        StackOverFlowQuestion question =
            stackOverFlowClient.fetchQuestion(Long.parseLong(matcher.group("id"))).block().getItems().getFirst();
        Timestamp lastActivity = question.getLastActivityAsTimestamp();

        if (lastActivity.after(link.getLastCheckTime())) {
            botClient.updateLink(link.getUrl(), List.of(link.getChatId()));
            jdbcLinkService.updateLinkLastCheckTime(link.getId(), now);
        }
    }

    private void updateGithubLink(Link link, Timestamp now) {
        String url = link.getUrl().toString();
        String owner = extractOwnerName(url);
        String repoName = extractRepoName(url);

        GitHubRepository rep = gitHubClient.getRepositoryInfo(owner, repoName).block();
        Timestamp lastPush = rep.getLastPush();

        if (lastPush.after(link.getLastCheckTime())) {
            botClient.updateLink(link.getUrl(), List.of(link.getChatId()));
            jdbcLinkService.updateLinkLastCheckTime(link.getId(), now);
        }
    }

    public static String extractOwnerName(String githubUrl) {
        Pattern pattern = Pattern.compile("github.com/(?<owner>[^/]+)/");
        Matcher matcher = pattern.matcher(githubUrl);

        if (matcher.find()) {
            return matcher.group("owner");
        } else {
            return null;
        }
    }

    public static String extractRepoName(String githubUrl) {
        Pattern pattern = Pattern.compile("/(?<repo>[^/]+)$");
        Matcher matcher = pattern.matcher(githubUrl);

        if (matcher.find()) {
            return matcher.group("repo");
        } else {
            return null;
        }
    }
}
