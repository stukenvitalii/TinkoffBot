package edu.java.bot.model;

import edu.java.bot.model.Request.AddLinkRequest;
import edu.java.bot.model.Request.RemoveLinkRequest;
import edu.java.bot.model.Response.ApiErrorResponse;
import edu.java.bot.model.Response.LinkResponse;
import edu.java.bot.model.Response.ListLinksResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class BotClient {
    private final String BASE_URL = "http://localhost:8080";

    private final WebClient webClient;

    public BotClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public ListLinksResponse getLinksById(Long chatId) {
        return webClient
            .get()
            .uri(BASE_URL + "/links")
            .header("Accept","application/json")
            .header("Tg-Chat-Id", String.valueOf(chatId))
            .retrieve()
            .bodyToMono(ListLinksResponse.class)
            .block();
    }

    public LinkResponse addLinkById(Long chatId, AddLinkRequest addLinkRequest) {
        return webClient
            .post()
            .uri(BASE_URL + "/links")
            .body(Mono.just(addLinkRequest), AddLinkRequest.class)
            .header("Accept","application/json")
            .header("Tg-Chat-Id", String.valueOf(chatId))
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }

    public LinkResponse deleteLinkById(Long chatId, RemoveLinkRequest removeLinkRequest) {
        return webClient
            .method(HttpMethod.DELETE)
            .uri(BASE_URL + "/links")
            .body(Mono.just(removeLinkRequest), RemoveLinkRequest.class)
            .header("Accept","application/json")
            .header("Tg-Chat-Id", String.valueOf(chatId))
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }

    public String addChatById(@PathVariable String chatId) {
        return webClient
            .post()
            .uri(BASE_URL + "/tg-chat/{id}",chatId)
            .header("Accept","application/json")
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

    public String deleteChatById(@PathVariable String chatId) {
        return webClient
            .delete()
            .uri(BASE_URL + "/tg-chat/{id}",chatId)
            .header("Accept","application/json")
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
}
