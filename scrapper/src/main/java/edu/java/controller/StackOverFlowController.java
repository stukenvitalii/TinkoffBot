package edu.java.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.dto.StackOverFlowQuestion;
import edu.java.dto.StackOverFlowResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


@RestController
public class StackOverFlowController {

    @Autowired
    private WebClient.Builder webClientBuilder;
    private final ObjectMapper objectMapper;

    public StackOverFlowController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping("/questions/{id}")
    public Flux<StackOverFlowQuestion> getQuestionById(@PathVariable("id") Long id) {
        return webClientBuilder
            .build()
            .get()
            .uri("https://api.stackexchange.com/2.3/questions/{id}?order=desc&sort=activity&site=stackoverflow", id)
            .accept(MediaType.APPLICATION_JSON)
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
