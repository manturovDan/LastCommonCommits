package lastCommonCommitsGitHub.HTTPInteraction;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class JSONHandler {
    private Gson gson;

    public JSONHandler() {
        gson = new Gson();
    }

    public long lastEventId(String eventsResponse) {
        Object obj = new JsonParser().parse(eventsResponse);
        JsonArray eventsArray = (JsonArray) obj;
        if (eventsArray.size() == 0) {
            return 0;
        }
        JsonObject lastEvent = (JsonObject) eventsArray.get(0);
        return lastEvent.get("id").getAsLong();
    }

    public String getBranchCommit(String branchResponse) {
        Object obj = new JsonParser().parse(branchResponse);
        JsonObject branch = (JsonObject) obj;
        JsonObject commit = (JsonObject) branch.get("commit");
        return commit.get("sha").getAsString();
    }
}
