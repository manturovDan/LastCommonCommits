package lastCommonCommitsGitHub.HTTPInteraction;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

public class LastEventGetter {
    private final HTTPGitHubMediator mediator;

    public LastEventGetter(HTTPGitHubMediator mediator) {
        this.mediator = mediator;
    }

    public long retrieveLastEventId() throws IOException {
        HttpRequest request = createRequestToGetLastEventId();
        String response = mediator.send(request);
        return mediator.getJSONHAndler().lastEventId(response);
    }

    private HttpRequest createRequestToGetLastEventId() {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(constructLastEventIdURI()));

        return mediator.createRequestWithAuth(builder);
    }

    private String constructLastEventIdURI() {
        return HTTPGitHub.GITHUB_API_LINK + "repos/" + mediator.getOwner()
                + "/" + mediator.getRepo() + "/events";
    }
}
