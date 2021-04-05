package lastCommonCommitsGitHub.HTTPInteraction;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Iterator;


public class JSONHandler {
    public static class JSONCommitParser implements Iterator<String> {
        private int commitNum;
        private final JsonArray commitsArray;

        JSONCommitParser(String response) {
            Object obj = new JsonParser().parse(response);
            commitsArray = (JsonArray) obj;
            commitNum = 0;
        }

        public String next() {
            if (commitNum == commitsArray.size())
                return null;

            JsonObject commit = (JsonObject) commitsArray.get(commitNum);
            commitNum++;
            return commit.get("sha").getAsString();
        }

        public boolean hasNext() {
            return commitNum != commitsArray.size();
        }
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

    public JSONCommitParser getCommits(String commitResponse) {
        return new JSONCommitParser(commitResponse);
    }
}
