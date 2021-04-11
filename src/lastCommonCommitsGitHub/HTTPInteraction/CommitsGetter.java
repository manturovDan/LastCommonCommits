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
        String response = getCommitsPage(branchName, 1);
        return new JSONHandler.JSONCommitsFeeder(response, branchName, 1, mediator);
    }

    String getCommitsPage(String branchName, int page) throws IOException {
        HttpRequest request = createCommitsRequest(branchName, page);
        return mediator.send(request);
    }

    private String constructCommitsURI(String branchName, int page) {
        return HTTPGitHub.GITHUB_API_LINK + "repos/" + mediator.getOwner()
                + "/" + mediator.getRepo() + "/commits?sha=" + branchName + "&per_page=100&page=" + page;
    }

    private HttpRequest createCommitsRequest(String branchName, int page) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(constructCommitsURI(branchName, page)));

        return mediator.createRequestWithAuth(builder);
    }

}
