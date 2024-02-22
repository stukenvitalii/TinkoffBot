package edu.java.scrapper.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.ScrapperApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.status;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ScrapperApplication.class})
@WireMockTest
public class StackOverFlowClientControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension
        .newInstance()
        .options(wireMockConfig().dynamicPort().dynamicPort())
        .build();

    @DynamicPropertySource
    public static void setUpMockBaseUrl(DynamicPropertyRegistry registry) {
        registry.add("stack-overflow-base-url", wireMockExtension::baseUrl);
    }

    @AfterEach
    void afterEach() {
        wireMockExtension.resetAll();
    }

    @Test
    public void testStatusCodePositive() {
        wireMockExtension.stubFor(WireMock.get(
                "/questions/13133695"
            )
            .willReturn(aResponse()
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withStatus(200)));

        webTestClient.get().uri("/questions/13133695")
            .exchange()
            .expectStatus()
            .isOk();
    }

    @Test
    public void testGetValidJson() {
        wireMockExtension.stubFor(WireMock.get("/questions/13133695")
            .willReturn(aResponse()
                .withBody("[\n" +
                    "    {\n" +
                    "        \"question_id\": 13133695,\n" +
                    "        \"is_answered\": true,\n" +
                    "        \"title\": \"IncompatibleClassChangeError with Eclipse Jetty\"\n" +
                    "    }\n" +
                    "]")
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withStatus(200)));

        webTestClient.get().uri("/questions/13133695")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody().json("[\n" +
                "    {\n" +
                "        \"question_id\": 13133695,\n" +
                "        \"is_answered\": true,\n" +
                "        \"title\": \"IncompatibleClassChangeError with Eclipse Jetty\"\n" +
                "    }\n" +
                "]");
    }

    @Test
    public void testIncorrectUrl() {
        wireMockExtension.stubFor(WireMock.get(
                "/questions/sdfg"
            )
            .willReturn(aResponse()
                .withBody("{}")
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withStatus(200)));

        webTestClient.get().uri("/questions/sdfg")
            .exchange()
            .expectStatus()
            .is4xxClientError();
    }
}
