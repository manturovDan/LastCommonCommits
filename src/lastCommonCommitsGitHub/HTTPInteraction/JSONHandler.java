package lastCommonCommitsGitHub.HTTPInteraction;

import java.util.*;
import org.json.*;

public class JSONHandler {
    public static class JSONCommitsFeeder implements Iterator<AbstractMap.SimpleEntry<String, List<String>>> {
        private int commitNum;
        private final JSONArray commitsArray;

        JSONCommitsFeeder(String response) {
            commitsArray = new JSONArray(response);
            commitNum = 0;
        }

        public AbstractMap.SimpleEntry<String, List<String>> next() {
            if (commitNum == commitsArray.length())
                return null;

            JSONObject commit = (JSONObject) commitsArray.get(commitNum);
            commitNum++;

            String commitSha = commit.getString("sha");
            List<String> parentsList = new ArrayList<>();

            JSONArray parents = (JSONArray) commit.get("parents");
            for (Object parentObj : parents) {
                JSONObject parentConcrete = (JSONObject) parentObj;
                parentsList.add(parentConcrete.getString("sha"));
            }

            return new AbstractMap.SimpleEntry<>(commitSha, parentsList);
        }

        public boolean hasNext() {
            return commitNum != commitsArray.length();
        }
    }

    public long lastEventId(String eventsResponse) {
        JSONArray eventsArray = new JSONArray(eventsResponse);
        if (eventsArray.length() == 0) {
            return 0;
        }
        JSONObject lastEvent = (JSONObject) eventsArray.get(0);
        return lastEvent.getLong("id");
    }

    public String getBranchCommit(String branchResponse) {
        JSONObject branch = new JSONObject(branchResponse);
        JSONObject commit = (JSONObject) branch.get("commit");
        return commit.getString("sha");
    }

}
