package edu.java.model;

import edu.java.model.Request.LinkUpdateRequest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;

public class ScrapperClient {
    private final String BASE_URL = "http://localhost:8090";

    private final WebClient webClient;

    public ScrapperClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public String updateLink(String url, List<Long> tgChatIds) {
        LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(1L, url, "Обновление ссылки", tgChatIds);

        return webClient
            .post()
            .uri(BASE_URL + "/updates")
            .body(Mono.just(linkUpdateRequest), LinkUpdateRequest.class)
            .header("Accept","application/json")
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

}
