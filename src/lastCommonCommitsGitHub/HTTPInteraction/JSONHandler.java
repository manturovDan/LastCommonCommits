package lastCommonCommitsGitHub.HTTPInteraction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class JSONHandler {
    public static class JSONCommitsFeeder {
        private int commitNum;
        private JSONArray commitsArray;
        private final String branchName;
        private int page;
        private final HTTPGitHubMediator mediator;

        JSONCommitsFeeder(String response, String branchName, int page, HTTPGitHubMediator mediator) throws IOException {
            try {
                commitsArray = new JSONArray(response);
                commitNum = 0;
                this.branchName = branchName;
                this.page = page;
                this.mediator = mediator;
            } catch (JSONException e) {
                throw new IOException("Json Parsing commits exception");
            }
        }

        public AbstractMap.SimpleEntry<String, List<String>> getNextCommit() throws IOException {
            if (!hasNextCommit())
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

        public boolean hasNextCommit() throws IOException {
            if (commitNum == commitsArray.length()) {
                commitNum = 0;
                String response = mediator.getCommitsGetter().getCommitsPage(branchName, ++page);
                if (response.equals("[]"))
                    return false;
                commitsArray = new JSONArray(response);
            }

            return true;
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
