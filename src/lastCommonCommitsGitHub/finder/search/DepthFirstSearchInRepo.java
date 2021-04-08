package lastCommonCommitsGitHub.finder.search;

import lastCommonCommitsGitHub.HTTPInteraction.HTTPGitHub;
import lastCommonCommitsGitHub.HTTPInteraction.JSONHandler;
import lastCommonCommitsGitHub.finder.storage.SearchStorage;

import java.util.AbstractMap;
import java.util.List;
import java.util.function.Function;

public class DepthFirstSearchInRepo {
    private SearchStorage storage;
    private final HTTPGitHub HTTPInteraction;

    public DepthFirstSearchInRepo(HTTPGitHub HTTPInteraction) {
        this.HTTPInteraction = HTTPInteraction;
        storage = new SearchStorage(HTTPInteraction.getRepo(), HTTPInteraction.lastEvent());
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
        long lastEventId = storage.getLastEvent();
        if (HTTPInteraction.lastEvent() != lastEventId) {
            storage = new SearchStorage(HTTPInteraction.getRepo(), lastEventId);
        }

        String topBranchA = storage.getRepositoryGraph().getTopCommit(branchA);
        String topBranchB = storage.getRepositoryGraph().getTopCommit(branchB);

        handleBranchesIfBothArentCached(branchA, topBranchA, branchB, topBranchB);
        handleBranchesIfAtAtLeastOneIsCached(branchA, topBranchA, branchB, topBranchB);

        System.out.println(storage.getLastCommonCommits());

    }

    private void handleBranchesIfBothArentCached(String branchA, String topBranchA,
                                                 String branchB, String topBranchB) {
        if (topBranchA == null && topBranchB == null) {
            topBranchA = buildGitGraph(branchB);
            storage.copyCommitsFromGraphToPreStoredBranch();
            topBranchB = buildGitGraph(branchB);

            storage.getRepositoryGraph().setTopCommit(branchA, topBranchA);
            storage.getRepositoryGraph().setTopCommit(branchB, topBranchB);

            depthFastSearch(topBranchB, this::handleCommitAsPreStored);
        }
    }

    private void handleBranchesIfAtAtLeastOneIsCached(String branchA, String topBranchA,
                                                      String branchB, String topBranchB) {
        if (topBranchB != null && topBranchA == null) {
            branchB = branchA;

            topBranchA = topBranchB;
            topBranchB = null;
        }

        depthFastSearch(topBranchA, this::addCommitInPreStored);

        if (topBranchB == null) {
            topBranchB = buildGitGraph(branchB);
        }

        depthFastSearch(topBranchB, this::handleCommitAsPreStored);
    }

    private void searchDeeper(Function<String, Function> action) {
        while (!storage.getDfsStack().isEmpty()) {
            String currentCommit = storage.getDfsStack().pop();
            action = action.apply(currentCommit);
        }
    }

    private Function<String, Function> addCommitInPreStored(String commit) {
        storage.getPreStoredBranch().add(commit);
        return this::addCommitInPreStored;
    }

    private Function<String, Function> handleCommitAsPreStored(String commit) {
        if (storage.getPreStoredBranch().contains(commit)) {
            if(!storage.getCommitsUnderLastCommon().contains(commit)) {
                storage.getLastCommonCommits().add(commit);
                //System.out.println(storage.getLastCommonCommits());
                pushCommitsListInStack(storage.getRepositoryGraph().getParents(commit));
                return this::handleCommitAsCommon;
            }
        }
        else {
            pushCommitsListInStack(storage.getRepositoryGraph().getParents(commit));
        }

        return this::handleCommitAsPreStored;
    }

    private Function<String, Function> handleCommitAsCommon(String commit) {
        if (!storage.getCommitsUnderLastCommon().contains(commit)) {
            storage.getCommitsUnderLastCommon().add(commit);
            storage.getLastCommonCommits().remove(commit);

            List<String> parents = storage.getRepositoryGraph().getParents(commit);
            if (parents.size() > 0) {
                pushCommitsListInStack(parents); //возврат в первую функцию, если нет родителей
                return this::handleCommitAsCommon;
            }
            else {
                return this::handleCommitAsPreStored;
            }
        }

        return this::handleCommitAsPreStored;
    }

    private void depthFastSearch(String topCommit, Function<String, Function> action) {
        storage.getDfsStack().push(topCommit);
        searchDeeper(action);
    }

    private void pushCommitsListInStack(List<String> commitsList) {
        for (String commit : commitsList) {
            storage.getDfsStack().push(commit);
        }
    }

    public long getLastEventId() {
        return storage.getLastEvent();
    }
}
