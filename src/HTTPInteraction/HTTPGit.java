package HTTPInteraction;

import java.net.http.HttpClient;

public interface HTTPGit {
    HttpClient getClient();
    String getOwner();
    String getRepo();
    String getToken();
}
