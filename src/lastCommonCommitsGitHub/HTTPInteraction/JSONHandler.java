package lastCommonCommitsGitHub.HTTPInteraction;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class JSONHandler {
    public long lastEventId(String response) {
        Object obj = new JsonParser().parse(response);
        JsonArray eventsArray = (JsonArray) obj;
        JsonObject lastEvent = (JsonObject) eventsArray.get(0);
        return lastEvent.get("id").getAsLong();
    }
}
