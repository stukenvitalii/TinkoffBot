package edu.java.stackoverflow;

import edu.java.exception.ClientException;
import edu.java.exception.ServerException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.RetryBackoffSpec;

public class StackOverFlowClient {

    private WebClient webClient;
    private final WebClient.Builder webClientBuilder = WebClient.builder();
    private static final String URL = "/questions/%d?site=stackoverflow";
    private static final String QUESTIONS_COMMENTS = "/questions/%d/comments?site=stackoverflow";

    @Autowired
    private RetryBackoffSpec retryBackoffSpec;

    public StackOverFlowClient(String baseurl) {
        webClient = webClientBuilder.baseUrl(baseurl)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE).build();
    }

    public StackOverFlowResponse fetchQuestion(long questionId) {
        String apiUrl = String.format(URL, questionId);
        String commentsUrl = String.format(QUESTIONS_COMMENTS, questionId);

        Long comments = (long) webClient
            .get()
            .uri(commentsUrl)
            .retrieve()
            .onStatus(
                HttpStatusCode::is5xxServerError,
                response -> Mono.error(new ServerException("Server error comments"))
            )
            .onStatus(
                HttpStatusCode::is4xxClientError,
                response -> Mono.error(new ClientException("Client error comments"))
            )
            .bodyToMono(StackOverFlowResponse.class)
            .retryWhen(retryBackoffSpec)
            .block().getItems().size();

        StackOverFlowResponse response = webClient
            .get()
            .uri(apiUrl)
            .retrieve()
            .onStatus(
                HttpStatusCode::is5xxServerError,
                resp -> Mono.error(new ServerException("Server error response"))
            )
            .onStatus(
                HttpStatusCode::is4xxClientError,
                resp -> Mono.error(new ClientException("Client error response"))
            )
            .bodyToMono(StackOverFlowResponse.class)
            .retryWhen(retryBackoffSpec)
            .block();

        StackOverFlowResponse list = new StackOverFlowResponse();
        StackOverFlowQuestion question = response.getItems().getFirst();
        question.setCommentCount(comments);
        list.setItems(List.of(question));

        return list;
    }
}
