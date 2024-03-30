package com.parking.vehicleparking.controller;

import com.parking.vehicleparking.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/github")
public class GitHubController {

    private final GitHubService gitHubService;

    @Autowired
    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/getFile/{owner}/{repo}/{path}/{branch}")
    public String getFileContent(
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable String path,
            @PathVariable String branch) {
        return gitHubService.getFileContent(owner, repo, path, branch);
    }

    @GetMapping("/searchFile/{owner}/{repo}/{file}")
    public List searchFile(
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable String file) {
        return gitHubService.searchFile(owner, repo, file);
    }
}
