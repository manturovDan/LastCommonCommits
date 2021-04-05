package lastCommonCommitsGitHub.finder.search;

import lastCommonCommitsGitHub.HTTPInteraction.HTTPGitHub;
import lastCommonCommitsGitHub.HTTPInteraction.JSONHandler;
import lastCommonCommitsGitHub.finder.storage.SearchStorage;

public class DeepFirstSearchInRepo {
    private SearchStorage storage;
    private HTTPGitHub HTTPInteraction;

    public DeepFirstSearchInRepo(HTTPGitHub HTTPInteraction) {
        this.HTTPInteraction = HTTPInteraction;
        storage = new SearchStorage(HTTPInteraction.getRepo());
    }

    public void buildGitGraph() {
        JSONHandler.JSONCommitParser commits = HTTPInteraction.getCommits();
        while (commits.hasNext()) {
            String commit = commits.next();
            System.out.println(commit);
        }
    }
}
