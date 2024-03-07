package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.stackoverflow.StackOverFlowClient;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class StackoverflowClientTest {
    private static WireMockServer wireMockServer;

    @BeforeAll
    public static void setUp() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    @DisplayName("test for check the required response body")
    public void testFetchQuestion() {
        // Arrange
        long questionId = 123456;
        String order = "activity";
        String sort = "desc";
        OffsetDateTime fixedTime = OffsetDateTime.parse("2022-02-21T12:34:56Z");

        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/questions/123456"))
            .willReturn(WireMock.aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\n" +
                    "    \"items\": [\n" +
                    "        {\n" +
                    "            \"is_answered\": true,\n" +
                    "            \"question_id\": 1,\n" +
                    "            \"title\": \"title\"\n" +
                    "        }\n" +
                    "    ]\n}")
            ));

        // Act
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:" + wireMockServer.port()).build();
        StackOverFlowClient stackOverflowClient = new StackOverFlowClient(webClient);

        // Assert
        StepVerifier.create(stackOverflowClient.fetchQuestion(questionId, sort, order))
            // Then
            .expectNextMatches(response -> response.getItems().getFirst().getTitle().equals("title") &&
                response.getItems().getFirst().getQuestionId() == 1 &&
                response.getItems().getFirst().isAnswered()
            )
            .expectComplete()
            .verify();
    }

}
