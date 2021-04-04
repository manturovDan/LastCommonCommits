package lastCommonCommitsGitHub.HTTPInteraction;

import java.net.http.HttpClient;

public class HTTPGitHubMediatorHub implements HTTPGitHubMediator {
    public static final String GITHUB_API_LINK = "https://api.github.com/";

    private final String owner;
    private final String repo;
    private final String token;
    private HttpClient client;

    private BranchGetter branchGetter;


    public HTTPGitHubMediatorHub(String owner, String repo, String token) {
        this.owner = owner;
        this.repo = repo;
        this.token = token;

        client = HttpClient.newHttpClient();

        initializeMediatorComponents();
    }

    private void initializeMediatorComponents() {
        branchGetter = new BranchGetter(this);
    }

    @Override
    public HttpClient getClient() {
        return client;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public String getRepo() {
        return repo;
    }

    @Override
    public String getToken() {
        return token;
    }

    public String branch(String branchName) {
        return branchGetter.retrieve(branchName);
    }
}
