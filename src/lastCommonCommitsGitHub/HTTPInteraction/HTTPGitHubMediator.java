package lastCommonCommitsGitHub.HTTPInteraction;

import java.io.IOException;
import java.net.http.HttpRequest;

public interface HTTPGitHubMediator {
    String getOwner();
    String getRepo();
    String getToken();
    JSONHandler getJSONHAndler();
    CommitsGetter getCommitsGetter();
    HttpRequest createRequestWithAuth(HttpRequest.Builder builder);
    String send(HttpRequest request) throws IOException;
}
