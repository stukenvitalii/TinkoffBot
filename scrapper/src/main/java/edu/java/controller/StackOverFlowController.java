package edu.java.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.dto.StackOverFlowQuestion;
import edu.java.dto.StackOverFlowResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

@RestController
public class StackOverFlowController {

    @Autowired
    private WebClient.Builder webClientBuilder;
    private final ObjectMapper objectMapper;

    public StackOverFlowController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping("/questions/{id}")
    public Mono<StackOverFlowQuestion> getQuestion(@PathVariable("id") String id) {
        return  webClientBuilder.
            build()
            .get()
            .uri("/questions/{id}?order=desc&sort=activity&site=stackoverflow&key={apiKey}", id, "apiKey") //TODO wtf?
            .retrieve()
            .bodyToMono(StackOverFlowQuestion.class);
    }

    @GetMapping("/questions/search/{title}")
    public Flux<StackOverFlowQuestion> getListOfQuestionsWithSpecifiedTitle(@PathVariable("title") String title) {
        return webClientBuilder
            .build()
            .get()
            .uri("https://api.stackexchange.com/2.2/search?order=desc&sort=activity&intitle={title}&site=stackoverflow", title)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class)
            .<List<StackOverFlowQuestion>>handle((json, sink) -> {
                try {
                    // Deserialize only the "items" array and ignore other fields
                    StackOverFlowResponse response = objectMapper.readValue(json, StackOverFlowResponse.class);
                    sink.next(response.getItems());
                } catch (Exception e) {
                    sink.error(new RuntimeException("Failed to parse JSON response", e));
                }
            })
            .flatMapIterable(items -> items);
    }
}
