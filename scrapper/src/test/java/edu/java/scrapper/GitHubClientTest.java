package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.github.GitHubClient;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.test.StepVerifier;
import reactor.util.retry.RetryBackoffSpec;

@SpringBootTest
public class GitHubClientTest {
    private static WireMockServer wireMockServer;

    @MockBean
    private RetryBackoffSpec retryBackoffSpec;

    @Autowired
    private GitHubClient gitHubClient;

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

//    @Test
//    @DisplayName("test for check the required response body")
//    @Ignore
//    public void testFetchRepository() {
//
//        String name = "testOwner";
//        String reposName = "testRepo";
//
//        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/repos/testOwner/testRepo"))
//            .willReturn(WireMock.aResponse()
//                .withStatus(200)
//                .withHeader("Content-Type", "application/json")
//                .withBody("{\"id\":756021540,\"name\":\"testRepo\",\"defaultBranch\":\"master\"}")
//            ));
//        String baseUrl = "http://localhost:" + wireMockServer.port();
//
//
//        StepVerifier.create(gitHubClient.getRepositoryInfo(name, reposName))
//            .expectNextMatches(repository -> repository.getName().equals("testRepo") &&
//                repository.getDefaultBranch().equals("master") &&
//                repository.getId() == 756021540
//            )
//            .expectComplete()
//            .verify();
//    }
}
