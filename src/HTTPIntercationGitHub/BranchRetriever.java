package HTTPIntercationGitHub;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BranchRetriever {
    private final HTTPGitHub mediator;

    BranchRetriever(HTTPGitHub mediator) {
        this.mediator = mediator;
    }

    String retrieve(String branchName) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(HTTPGitHub.GITHUB_API_LINK + "manturovDan/AugmentedRealityRecon/tree/rasterization"))
                .build();

        String res = mediator.getClient().sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body).join();

        return res;
    }
}
