package lastCommonCommitsGitHub.HTTPInteraction;

import java.net.URI;
import java.net.http.HttpRequest;

public class LastEventGetter {
    private final HTTPGitHubMediator mediator;

    public LastEventGetter(HTTPGitHubMediator mediator) {
        this.mediator = mediator;
    }

    public long retrieve() {
        HttpRequest request = createRequest();
        String response = mediator.send(request);
        return mediator.getJSONHAndler().lastEventId(response);
    }

    private HttpRequest createRequest() {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(constructURI()));

        return mediator.createRequestWithAuth(builder);
    }

    private String constructURI() {
        return HTTPGitHub.GITHUB_API_LINK + "repos/" + mediator.getOwner()
                + "/" + mediator.getRepo() + "/events";
    }
}