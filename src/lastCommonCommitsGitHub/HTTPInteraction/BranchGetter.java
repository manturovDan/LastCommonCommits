package lastCommonCommitsGitHub.HTTPInteraction;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BranchGetter {
    private final HTTPGitHubMediator mediator;

    BranchGetter(HTTPGitHubMediator mediator) {
        this.mediator = mediator;
    }

    //retrieves top commit of the branch
    String retrieve(String branchName) {
        HttpRequest request = createRequest(branchName);
        return mediator.send(request);
    }

    private String constructURI(String branchName) {
        return HTTPGitHub.GITHUB_API_LINK + "repos/" + mediator.getOwner()
                + "/" + mediator.getRepo() + "/branches/" + branchName;
    }

    private HttpRequest createRequest(String branchName) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(constructURI(branchName)));

        return mediator.createRequestWithAuth(builder);
    }
}
