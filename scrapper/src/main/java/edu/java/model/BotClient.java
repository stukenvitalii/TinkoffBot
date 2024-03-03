package edu.java.model;

import edu.java.model.Request.LinkUpdateRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class BotClient {
    private final String baseUrl = "http://localhost:8090";

    private final WebClient webClient;

    public BotClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public String updateLink(String url, List<Long> tgChatIds) throws MalformedURLException {
        LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(1L, new URL(url), "Обновление ссылки", tgChatIds);

        return webClient
            .post()
            .uri(baseUrl + "/updates")
            .body(Mono.just(linkUpdateRequest), LinkUpdateRequest.class)
            .header("Accept", "application/json")
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

}
