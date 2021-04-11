package lastCommonCommitsGitHub.HTTPInteraction;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

public class CommitsGetter {
    private final HTTPGitHubMediator mediator;

    CommitsGetter(HTTPGitHubMediator mediator) {
        this.mediator = mediator;
    }

    public JSONHandler.JSONCommitsFeeder retrieveCommits(String branchName) throws IOException {
        HttpRequest request = createCommitsRequest(branchName);
        String response = mediator.send(request);

        return new JSONHandler.JSONCommitsFeeder(response);
    }

    private String constructCommitsURI(String branchName) {
        return HTTPGitHub.GITHUB_API_LINK + "repos/" + mediator.getOwner()
                + "/" + mediator.getRepo() + "/commits?sha=" + branchName + "&per_page=100";
    }

    private HttpRequest createCommitsRequest(String branchName) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(constructCommitsURI(branchName)));

        return mediator.createRequestWithAuth(builder);
    }

}
