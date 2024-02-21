package edu.java.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.dto.StackOverFlowQuestion;
import edu.java.dto.StackOverFlowResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


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
    public Flux<StackOverFlowQuestion> getQuestionById(@PathVariable("id") Long id) {
        return webClient
            .get()
            .uri(x -> x.path("/questions/{id}")
                .queryParam("order", "desc")
                .queryParam("sort", "activity")
                .queryParam("site", "stackoverflow")
                .build(id))
            .retrieve()
            .bodyToMono(String.class)
            .<List<StackOverFlowQuestion>>handle((json, sink) -> {
                try {
                    StackOverFlowResponse response = objectMapper.readValue(json, StackOverFlowResponse.class);
                    sink.next(response.getItems());
                } catch (Exception e) {
                    sink.error(new RuntimeException("Failed to parse JSON response", e));
                }
            })
            .flatMapIterable(items -> items); //TODO сделать ответ в виде объекта, а не списка!
    }
}
