package com.parking.vehicleparking.service;

import com.parking.vehicleparking.response.GitHubResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GitHubService {

    @Value("${github.api.base-url}")
    private String githubApiBaseUrl;

    RestTemplate restTemplate = new RestTemplate();

    public String getFileContent(String owner, String repo, String path, String branch) {
        String apiUrl = String.format("%s/repos/%s/%s/contents/%s?ref=%s", githubApiBaseUrl, owner, repo, path, branch);

        GitHubResponse response = restTemplate.getForObject(apiUrl, GitHubResponse.class);

        String fileResponse = restTemplate.getForObject(response.getDownload_url(), String.class);

        return fileResponse;

    }

    public List<GitHubSearchResultItem> searchFile(String owner, String repo, String fileName) {
        String apiUrl = String.format("%s/search/code?q=%s+repo:%s/%s", githubApiBaseUrl, fileName, owner, repo);

        // Fetch search results
        GitHubSearchResponse response = restTemplate.getForObject(apiUrl, GitHubSearchResponse.class);

        // Return the list of search results
        return response.getItems();
    }

    private static class GitHubSearchResponse {
        private List<GitHubSearchResultItem> items;

        public List<GitHubSearchResultItem> getItems() {
            return items;
        }

        public void setItems(List<GitHubSearchResultItem> items) {
            this.items = items;
        }
    }

    private static class GitHubSearchResultItem {
        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
