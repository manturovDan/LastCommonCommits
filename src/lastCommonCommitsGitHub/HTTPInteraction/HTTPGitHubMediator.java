package lastCommonCommitsGitHub.HTTPInteraction;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public interface HTTPGitHubMediator {
    HttpClient getClient();
    String getOwner();
    String getRepo();
    String getToken();
    JSONHandler getJSONHAndler();
    HttpRequest createRequestWithAuth(HttpRequest.Builder builder);
    String send(HttpRequest request) throws IOException, InterruptedException;
}
