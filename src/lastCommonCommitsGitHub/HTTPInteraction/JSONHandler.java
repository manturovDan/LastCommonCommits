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

    public long lastEventId(String response) {
        Object obj = new JsonParser().parse(response);
        JsonArray eventsArray = (JsonArray) obj;
        if (eventsArray.size() == 0) {
            return 0;
        }
        JsonObject lastEvent = (JsonObject) eventsArray.get(0);
        return lastEvent.get("id").getAsLong();
    }
}
