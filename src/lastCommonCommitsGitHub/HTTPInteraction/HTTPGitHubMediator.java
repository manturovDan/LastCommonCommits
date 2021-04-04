package lastCommonCommitsGitHub.HTTPInteraction;

import java.net.http.HttpClient;

public interface HTTPGitHubMediator {
    HttpClient getClient();
    String getOwner();
    String getRepo();
    String getToken();
}
