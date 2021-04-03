package HTTPIntercationGitHub;

import java.net.http.HttpClient;

public class HTTPGitHub {
    public static final String GITHUB_API_LINK = "https://api.github.com/";

    private final String owner;
    private final String repo;
    private final String token;
    private HttpClient client;

    private BranchRetriever branchRetriever;


    public HTTPGitHub(String owner, String repo, String token) {
        this.owner = owner;
        this.repo = repo;
        this.token = token;

        client = HttpClient.newHttpClient();
        initializeMediatorComponents();
    }

    private void initializeMediatorComponents() {
        branchRetriever = new BranchRetriever(this);
    }

    public HttpClient getClient() {
        return client;
    }
}
