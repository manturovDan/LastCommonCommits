package lastCommonCommitsGitHub.finder.search;

import lastCommonCommitsGitHub.HTTPInteraction.HTTPGitHub;
import lastCommonCommitsGitHub.HTTPInteraction.JSONHandler;
import lastCommonCommitsGitHub.finder.storage.SearchStorage;

import java.util.AbstractMap;
import java.util.List;
import java.util.function.Consumer;

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

        storage.getDfsStack().push(topBranchB);

        searchDeeper(this::handleCommitAsPreStored);

        System.out.println(storage.getLastCommonCommits());
    }

    private void searchDeeper(Consumer<String> action) {
        while (!storage.getDfsStack().isEmpty()) {
            String currentCommit = storage.getDfsStack().pop();
            action.accept(currentCommit);//make action here
        }
    }

    private void handleCommitAsPreStored(String commit) {
        if (storage.getPreStoredBranch().contains(commit)) {
           if(!storage.getCommitsUnderLastCommon().contains(commit)) {
               storage.getLastCommonCommits().add(commit);
               searchDeeper(this::handleCommitAsCommon);
           }
        }
        else {
            pushCommitsListInStack(storage.getRepositoryGraph().getParents(commit));
        }
    }

    private void handleCommitAsCommon(String commit) {
        if (!storage.getCommitsUnderLastCommon().contains(commit)) {
            storage.getCommitsUnderLastCommon().add(commit);
            storage.getLastCommonCommits().remove(commit);

            pushCommitsListInStack(storage.getRepositoryGraph().getParents(commit));
        }
    }

    private void pushCommitsListInStack(List<String> commitsList) {
        for (String commit : commitsList) {
            storage.getDfsStack().push(commit);
        }
    }
}
