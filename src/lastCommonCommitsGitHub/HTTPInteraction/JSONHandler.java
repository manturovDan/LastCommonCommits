package lastCommonCommitsGitHub.HTTPInteraction;

import java.io.IOException;
import java.util.*;
import org.json.*;

public class JSONHandler {
    public static class JSONCommitsFeeder {
        private int commitNum;
        private final JSONArray commitsArray;

        JSONCommitsFeeder(String response) throws IOException {
            try {
                commitsArray = new JSONArray(response);
                commitNum = 0;
            } catch (JSONException e) {
                throw new IOException("Json Parsing commits exception");
            }
        }

        public AbstractMap.SimpleEntry<String, List<String>> getNextCommit() throws IOException {
            if (commitNum == commitsArray.length())
                return null;

            try {
                JSONObject commit = (JSONObject) commitsArray.get(commitNum);
                commitNum++;

                String commitSha = commit.getString("sha");
                List<String> parentsList = constructParentsList(commit);

                return new AbstractMap.SimpleEntry<>(commitSha, parentsList);
            } catch (JSONException e) {
                throw new IOException("Json Parsing concrete commit exception");
            }
        }

        private List<String> constructParentsList(JSONObject commit) {
            List<String> parentsList = new ArrayList<>();
            JSONArray parents = (JSONArray) commit.get("parents");
            for (Object parentObj : parents) {
                JSONObject parentConcrete = (JSONObject) parentObj;
                parentsList.add(parentConcrete.getString("sha"));
            }
            return parentsList;
        }

        public boolean hasNextCommit() {
            return commitNum != commitsArray.length();
        }
    }

    public long lastEventId(String eventsResponse) throws IOException {
        try {
            JSONArray eventsArray = new JSONArray(eventsResponse);
            if (eventsArray.length() == 0) {
                return 0;
            }
            JSONObject lastEvent = (JSONObject) eventsArray.get(0);
            return lastEvent.getLong("id");
        } catch (JSONException e) {
            throw new IOException("Json Parsing Last Event exception");
        }
    }

}
