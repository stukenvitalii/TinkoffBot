package edu.java.controller;

import edu.java.service.GitHubService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MyController {
    private final GitHubService gitHubService;

    public MyController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/repository-info/{owner}/{repoName}")
    public Mono<String> getRepositoryJson(@PathVariable String owner, @PathVariable String repoName) {
        return gitHubService.getRepositoryJson(owner, repoName)
            .doOnNext(System.out::println); // Вывод JSON в консоль
    }
}
