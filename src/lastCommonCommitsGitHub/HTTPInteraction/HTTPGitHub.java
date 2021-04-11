package lastCommonCommitsGitHub.HTTPInteraction;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HTTPGitHub implements HTTPGitHubMediator {
    public static final String GITHUB_API_LINK = "https://api.github.com/";

    private final String owner;
    private final String repo;
    private String token;
    private final HttpClient client;

    private LastEventGetter lastEventGetter;
    private CommitsGetter commitsGetter;
    private JSONHandler jsonHandler;

    public HTTPGitHub(String owner, String repo, String token) {
        this.owner = owner;
        this.repo = repo;
        this.token = token;

        client = HttpClient.newHttpClient();

        initializeMediatorComponents();
    }

    private void initializeMediatorComponents() {
        lastEventGetter = new LastEventGetter(this);
        commitsGetter = new CommitsGetter(this);
        jsonHandler = new JSONHandler();
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

    @Override
    public CommitsGetter getCommitsGetter() {
        return commitsGetter;
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
    public String send(HttpRequest request) throws IOException {
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            throw new IOException("Error in sending request to GitHub server");
        }

        if (response.statusCode() / 100 != 2)
            throw new IOException("GitHub server returned a error :(\n" + response.body());

        return response.body();
    }

    public long lastEvent() throws IOException {
        return lastEventGetter.retrieveLastEventId();
    }

    public JSONHandler.JSONCommitsFeeder getCommits(String branchName) throws IOException {
        return commitsGetter.retrieveCommits(branchName);
    }

    public void setToken(String token) {
        this.token = token;
    }
}
