package edu.java.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.dto.StackOverFlowQuestion;
import edu.java.dto.StackOverFlowResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
public class StackOverFlowController {

    @Qualifier("stackOverFlowClient")
    @Autowired
    private WebClient webClient;
    private final ObjectMapper objectMapper;

    public StackOverFlowController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping(value = {"/questions/{id}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<StackOverFlowQuestion> getQuestionById(@PathVariable("id") Long id) {
        return webClient
            .get()
            .uri(x -> x.path("/questions/{id}")
                .queryParam("order", "desc")
                .queryParam("sort", "activity")
                .queryParam("site", "stackoverflow")
                .build(id))
            .retrieve()
            .bodyToMono(StackOverFlowResponse.class)
            .flatMap(response -> {
                if (response.getItems() != null && !response.getItems().isEmpty()) {
                    return Mono.just(response.getItems().getFirst());
                } else {
                    return Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Question not found for id: " + id));
                }
            })
            .onErrorResume(e -> {
                if (e instanceof ResponseStatusException) {
                    return Mono.error(e);
                } else {
                    return Mono.error(new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"));
                }
            });
    }
}
