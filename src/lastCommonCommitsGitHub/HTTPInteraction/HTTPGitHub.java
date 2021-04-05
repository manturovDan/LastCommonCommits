package lastCommonCommitsGitHub.HTTPInteraction;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HTTPGitHub implements HTTPGitHubMediator {
    public static final String GITHUB_API_LINK = "https://api.github.com/";

    private final String owner;
    private final String repo;
    private final String token;
    private HttpClient client;

    private BranchGetter branchGetter;
    private LastEventGetter lastEventGetter;
    private JSONHandler jsonHandler;

    public HTTPGitHub(String owner, String repo, String token) {
        this.owner = owner;
        this.repo = repo;
        this.token = token;

        client = HttpClient.newHttpClient();

        initializeMediatorComponents();
    }

    private void initializeMediatorComponents() {
        branchGetter = new BranchGetter(this);
        lastEventGetter = new LastEventGetter(this);
        jsonHandler = new JSONHandler();
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

    @Override
    public JSONHandler getJSONHAndler() {
        return jsonHandler;
    }

    public HttpRequest createRequestWithAuth(HttpRequest.Builder builder) {
        if (token.equals("")) {
            return builder.build();
        }
        else {
            return builder.header("Authorization", "Bearer " + token).build();
        }
    }

    @Override
    public String send(HttpRequest request) {
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body).join();
    }

    public String branch(String branchName) {
        return branchGetter.retrieve(branchName);
    }

    public long lastEvent() {
        return lastEventGetter.retrieve();
    }
}
