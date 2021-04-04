package lastCommonCommitsGitHub.HTTPInteraction;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public interface HTTPGitHubMediator {
    HttpClient getClient();
    String getOwner();
    String getRepo();
    String getToken();
    HttpRequest createRequestWithAuth(HttpRequest.Builder builder);
    String send(HttpRequest request);
}
