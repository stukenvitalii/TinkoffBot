package edu.java.client;

import edu.java.exception.ClientException;
import edu.java.exception.ServerException;
import edu.java.model.request.LinkUpdateRequest;
import edu.java.service.sender.SenderService;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.RetryBackoffSpec;

public class BotClient implements SenderService {
    private final String baseUrl = "http://localhost:8090";

    private final WebClient webClient = WebClient.builder().build();

    private final RetryBackoffSpec retryBackoffSpec;

    public BotClient(RetryBackoffSpec retryBackoffSpec) {
        this.retryBackoffSpec = retryBackoffSpec;
    }

    public void updateLink(LinkUpdateRequest linkUpdateRequest) {
        webClient
            .post()
            .uri(baseUrl + "/updates")
            .body(Mono.just(linkUpdateRequest), LinkUpdateRequest.class)
            .header("Accept", "application/json")
            .retrieve()
            .onStatus(
                HttpStatusCode::is5xxServerError,
                response -> Mono.error(new ServerException("Server error"))
            )
            .onStatus(
                HttpStatusCode::is4xxClientError,
                response -> Mono.error(new ClientException("Client error"))
            )
            .bodyToMono(String.class)
            .retryWhen(retryBackoffSpec)
            .block();
    }

}
