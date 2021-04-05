package lastCommonCommitsGitHub.HTTPInteraction;

import java.net.URI;
import java.net.http.HttpRequest;

public class CommitsGetter {
    private final HTTPGitHubMediator mediator;

    CommitsGetter(HTTPGitHubMediator mediator) {
        this.mediator = mediator;
    }

    public String retrieve() {
        HttpRequest request = createRequest();
        String response = mediator.send(request);

        return response;
    }

    private String constructURI() {
        return HTTPGitHub.GITHUB_API_LINK + "repos/" + mediator.getOwner()
                + "/" + mediator.getRepo() + "/commits";
    }

    private HttpRequest createRequest() {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(constructURI()));

        return mediator.createRequestWithAuth(builder);
    }

}
