package edu.java.scrapper.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.ScrapperApplication;
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
public class GitHubClientControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension
        .newInstance()
        .options(wireMockConfig().dynamicPort().dynamicPort())
        .build();

    @DynamicPropertySource
    public static void setUpMockBaseUrl(DynamicPropertyRegistry registry) {
        registry.add("git-hub-base-url", wireMockExtension::baseUrl);
    }

//    @Test
//    @Order(0)
//    public void testStatusIfUnauthorized() {
//        wireMockExtension.stubFor(WireMock.get(
//                "/repos/stukenvitalii/TinkoffBot"
//            )
//            .willReturn(aResponse()
//                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
//                .withBody("{}")
//                .withStatus(401)));
//
//        webTestClient.get().uri("/repos/stukenvitalii/TinkoffBot")
//            .exchange()
//            .expectStatus()
//            .is5xxServerError();
//    }

    @Test
    @Order(1)
    public void testStatusCodePositive() {
        wireMockExtension.stubFor(WireMock.get(
            "/repos/stukenvitalii/TinkoffBot"
        ).withHeader("Authorization", WireMock.equalTo("Bearer " + System.getenv("GITHUB_API_TOKEN_SECOND")))
            .willReturn(aResponse()
            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .withStatus(200)));

        webTestClient.get().uri("/repos/stukenvitalii/TinkoffBot")
            .exchange()
            .expectStatus()
            .isOk();
    }

    @Test
    @Order(2)
    public void testGetValidJson() {
        wireMockExtension.stubFor(WireMock.get(
                "/repos/stukenvitalii/TinkoffBot"
            ).withHeader("Authorization", WireMock.equalTo("Bearer " + System.getenv("GITHUB_API_TOKEN_SECOND")))
            .willReturn(aResponse()
                .withBody("{\"id\":756021540,\"name\":\"TinkoffBot\",\"defaultBranch\":null}")
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withStatus(200)));

        webTestClient.get().uri("/repos/stukenvitalii/TinkoffBot")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody().json("{\"id\":756021540,\"name\":\"TinkoffBot\",\"defaultBranch\":null}");
    }

    @Test
    @Order(3)
    public void testIncorrectUrl() {
        wireMockExtension.stubFor(WireMock.get(
                "/repos/stukenvitalii/TinkoffBot1"
            ).withHeader("Authorization", WireMock.equalTo("Bearer " + System.getenv("GITHUB_API_TOKEN_SECOND")))
            .willReturn(aResponse()
                .withBody("{}")
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withStatus(200)));

        webTestClient.get().uri("/repos/stukenvitalii/TinkoffBot1")
            .exchange()
            .expectStatus()
            .is5xxServerError();
    }
}
