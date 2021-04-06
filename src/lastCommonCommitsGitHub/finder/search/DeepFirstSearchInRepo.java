package lastCommonCommitsGitHub.finder.search;

import lastCommonCommitsGitHub.HTTPInteraction.HTTPGitHub;
import lastCommonCommitsGitHub.HTTPInteraction.JSONHandler;
import lastCommonCommitsGitHub.finder.storage.SearchStorage;

import java.util.AbstractMap;
import java.util.List;

public class DeepFirstSearchInRepo {
    private final SearchStorage storage;
    private HTTPGitHub HTTPInteraction;

    public DeepFirstSearchInRepo(HTTPGitHub HTTPInteraction) {
        this.HTTPInteraction = HTTPInteraction;
        storage = new SearchStorage(HTTPInteraction.getRepo());
    }

    public String buildGitGraph(String branchName) {
        JSONHandler.JSONCommitParser commits = HTTPInteraction.getCommits(branchName);
        String topCommit = null;
        while (commits.hasNext()) {
            AbstractMap.SimpleEntry<String, List<String>> commit = commits.next();
            storage.addCommitInRepo(commit);
            if (topCommit == null)
                topCommit = commit.getKey();
        }

        return topCommit;
    }

    public void lastCommonCommits(String branchA, String branchB) {
        String topBranchA = buildGitGraph(branchA);
        storage.copyCommitsFromGraphToPreStoredBranch();
        String topBranchB = buildGitGraph(branchB);

        storage.pushInStack(topBranchB);
    }
}
