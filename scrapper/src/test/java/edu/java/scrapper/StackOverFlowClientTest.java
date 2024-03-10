package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.stackoverflow.StackOverFlowClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class StackOverFlowClientTest {
    private static WireMockServer wireMockServer;

    @BeforeAll
    public static void setUp() {
        wireMockServer = new WireMockServer(8080);
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
        long questionId = 123456;

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
        String baseUrl ="http://localhost/:" + wireMockServer.port();
        StackOverFlowClient stackOverflowClient = new StackOverFlowClient(baseUrl);

        // Assert
        StepVerifier.create(stackOverflowClient.fetchQuestion(questionId))
            // Then
            .expectNextMatches(response -> response.getItems().getFirst().getTitle().equals("title") &&
                response.getItems().getFirst().getQuestionId() == 1 &&
                response.getItems().getFirst().isAnswered()
            )
            .expectComplete()
            .verify();
    }

}
