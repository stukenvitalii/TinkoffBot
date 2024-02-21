package edu.java.scrapper.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;

@WireMockTest
public class GitHubClientControllerTest {

    WireMockServer wireMockServer;

    @BeforeEach
    public void setup () {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
        setupStub();
    }

    @AfterEach
    public void teardown () {
        wireMockServer.stop();
    }

    public void setupStub() {
        wireMockServer.stubFor(get(urlEqualTo("/repos/stukenvitalii/TinkoffBot"))
            .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                .withStatus(200)
                .withBodyFile("json/glossary.json")));
    }

    @Test
    public void testStatusCodePositive() {
        given().
            when().
            get("http://localhost:8080/repos/stukenvitalii/TinkoffBot").
            then().
            assertThat().statusCode(200);
    }
}
