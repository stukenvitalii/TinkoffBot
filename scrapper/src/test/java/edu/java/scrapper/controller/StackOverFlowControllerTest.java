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
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.status;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ScrapperApplication.class})
@AutoConfigureWebTestClient(timeout = "10000")
@WireMockTest
public class StackOverFlowControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension
        .newInstance()
        .options(wireMockConfig().dynamicPort().dynamicPort())
        .build();

    @DynamicPropertySource
    public static void setUpMockBaseUrl(DynamicPropertyRegistry registry) {
        registry.add("app.stack-overflow-base-url", wireMockExtension::baseUrl);
    }

    @AfterEach
    void afterEach() {
        wireMockExtension.resetAll();
    }

    @Test
    public void testStatusCodePositive() {
        wireMockExtension.stubFor(WireMock.get("/questions/13133695?order=desc&sort=activity&site=stackoverflow")
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            ));

        webTestClient.get().uri("/questions/13133695")
            .exchange()
            .expectStatus()
            .isOk();
    }

    @Test
    void testGetValidJson() {
        wireMockExtension.stubFor(WireMock.get(urlEqualTo("/questions/7854933?order=desc&sort=activity&site=stackoverflow"))
            .willReturn(aResponse()
                .withBody("{\n" +
                    "    \"items\": [\n" +
                    "        {\n" +
                    "            \"is_answered\": true,\n" +
                    "            \"question_id\": 7854933,\n" +
                    "            \"title\": \"will linq to objects block the thread?\"\n" +
                    "        }\n" +
                    "    ]\n}"
                )
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withStatus(200)));

        webTestClient.get().uri("/questions/7854933")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.question_id").isEqualTo(7854933)
            .jsonPath("$.is_answered").isEqualTo(true)
            .jsonPath("$.title").isEqualTo("will linq to objects block the thread?");
    }

    @Test
    public void testIncorrectUrl() {
        wireMockExtension.stubFor(WireMock.get(urlEqualTo("/questions/456?order=desc&sort=activity&site=stackoverflow"))
            .willReturn(aResponse()
                .withBody("{\n" +
                    "   \"items\": []\n" +
                    "}")
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withStatus(404)));

        webTestClient.get().uri("/questions/456")
            .exchange()
            .expectStatus()
            .is5xxServerError(); //TODO разобраться как тут получить 404 и обработать его
    }
}
