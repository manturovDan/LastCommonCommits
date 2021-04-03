package HTTPInteraction.HTTPIntercationGitHub;

import HTTPInteraction.HTTPGit;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BranchRetriever {
    private final HTTPGit mediator;

    BranchRetriever(HTTPGit mediator) {
        this.mediator = mediator;
    }

    String retrieve(String branchName) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(constructURI(branchName)))
                .build();

        String res = mediator.getClient().sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body).join();

        return res;
    }

    private String constructURI(String branchName) {
        return HTTPGitHub.GITHUB_API_LINK + "repos/" + mediator.getOwner()
                + "/" + mediator.getRepo() + "/branches/" + branchName;
    }
}
