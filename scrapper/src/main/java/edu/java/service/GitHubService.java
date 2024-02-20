package edu.java.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GitHubService {
    private final WebClient webClient;

    public GitHubService() {
        this.webClient = WebClient.create("https://api.github.com");
    }
}
