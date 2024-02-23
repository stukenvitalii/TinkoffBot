package edu.java.scrapper.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.ScrapperApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.io.IOException;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ScrapperApplication.class})
@AutoConfigureWebTestClient(timeout = "10000")
@WireMockTest
public class GitHubControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension
        .newInstance()
        .options(wireMockConfig().dynamicPort().dynamicPort())
        .build();

    @DynamicPropertySource
    public static void setUpMockBaseUrl(DynamicPropertyRegistry registry) {
        registry.add("app.git-hub-base-url", wireMockExtension::baseUrl);
    }

    @AfterEach
    void afterEach() {
        wireMockExtension.resetAll();
    }

    @Test
    public void testStatusCodePositive() {
        wireMockExtension.stubFor(WireMock.get(WireMock.urlEqualTo(
                "/repos/stukenvitalii/TinkoffBot")
            ).withHeader("Authorization", WireMock.equalTo("Bearer " + System.getenv("GITHUB_API_TOKEN_SECOND")))
            .willReturn(aResponse()
                .withStatus(200)
                .withBody("{}")
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            ));

        webTestClient.get().uri("/repos/stukenvitalii/TinkoffBot")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody().json("{}");
    }

    @Test
    public void testGetValidJson() throws IOException {
        wireMockExtension.stubFor(WireMock.get(
                "/repos/stukenvitalii/TinkoffBot"
            ).withHeader("Authorization", WireMock.equalTo("Bearer " + System.getenv("GITHUB_API_TOKEN_SECOND")))
            .willReturn(aResponse()
                .withStatus(200)
                .withBody("{\"id\":756021540,\"name\":\"TinkoffBot\",\"defaultBranch\":null}")
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            ));

        webTestClient.get().uri("/repos/stukenvitalii/TinkoffBot")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody().json("{\"id\":756021540,\"name\":\"TinkoffBot\",\"defaultBranch\":null}");
    }

    @Test
    public void testIncorrectUrl() {
        wireMockExtension.stubFor(WireMock.get(
                "/repos/stukenvitalii/TinkoffBot1"
            ).withHeader("Authorization", WireMock.equalTo("Bearer " + System.getenv("GITHUB_API_TOKEN_SECOND")))
            .willReturn(aResponse()
                .withStatus(500)
                .withBody("{}")
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            ));

        webTestClient.get().uri("/repos/stukenvitalii/TinkoffBot1")
            .exchange()
            .expectStatus()
            .is5xxServerError();
    }
}
