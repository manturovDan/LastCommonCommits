package lastCommonCommitsGitHub.HTTPInteraction;

import java.net.URI;
import java.net.http.HttpRequest;

public class CommitsGetter {
    private final HTTPGitHubMediator mediator;

    CommitsGetter(HTTPGitHubMediator mediator) {
        this.mediator = mediator;
    }

    public JSONHandler.JSONCommitsFeeder retrieveCommits(String branchName) {
        HttpRequest request = createRequest(branchName);
        String response = mediator.send(request);

        return new JSONHandler.JSONCommitsFeeder(response);
    }

    private String constructURI(String branchName) {
        return HTTPGitHub.GITHUB_API_LINK + "repos/" + mediator.getOwner()
                + "/" + mediator.getRepo() + "/commits?sha=" + branchName;
    }

    private HttpRequest createRequest(String branchName) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(constructURI(branchName)));

        return mediator.createRequestWithAuth(builder);
    }

}
