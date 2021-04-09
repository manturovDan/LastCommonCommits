package lastCommonCommitsGitHub.finder.search;

import lastCommonCommitsGitHub.HTTPInteraction.HTTPGitHub;
import lastCommonCommitsGitHub.HTTPInteraction.JSONHandler;
import lastCommonCommitsGitHub.finder.storage.SearchStorage;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class DepthFirstSearchInRepo {
    private SearchStorage storage;
    private final HTTPGitHub HTTPInteraction;

    public DepthFirstSearchInRepo(HTTPGitHub HTTPInteraction) throws IOException {
        this.HTTPInteraction = HTTPInteraction;
        storage = new SearchStorage(HTTPInteraction.getRepo(), HTTPInteraction.lastEvent());
    }

    public String buildGitGraph(String branchName) throws IOException {
        JSONHandler.JSONCommitsFeeder commits = HTTPInteraction.getCommits(branchName);
        String topCommit = null;
        while (commits.hasNextCommit()) {
            AbstractMap.SimpleEntry<String, List<String>> commit = commits.getNextCommit();
            storage.addCommitInRepo(commit);
            if (topCommit == null)
                topCommit = commit.getKey();
        }

        return topCommit;
    }

    public Collection<String> lastCommonCommits(String branchA, String branchB) throws IOException {
        long lastEventId = storage.getLastEvent();
        if (HTTPInteraction.lastEvent() != lastEventId) {
            storage = new SearchStorage(HTTPInteraction.getRepo(), lastEventId);
        }

        String topBranchA = storage.getRepositoryGraph().getTopCommit(branchA);
        String topBranchB = storage.getRepositoryGraph().getTopCommit(branchB);

        handleBranchesIfAtAtLeastOneIsCached(branchA, topBranchA, branchB, topBranchB);
        handleBranchesIfBothArentCached(branchA, topBranchA, branchB, topBranchB);

        Collection<String> lastCommonCommits = storage.getLastCommonCommits().getList();

        clearIntermediateData();

        return lastCommonCommits;
    }

    private void clearIntermediateData() {
        storage.getPreStoredBranch().clear();
        storage.getCommitsUnderLastCommon().clear();
        storage.getLastCommonCommits().clear();
    }

    private void handleBranchesIfBothArentCached(String branchA, String topBranchA,
                                                 String branchB, String topBranchB) throws IOException {
        if (topBranchA == null && topBranchB == null) {
            topBranchA = buildGitGraph(branchA);
            storage.copyCommitsFromGraphToPreStoredBranch();
            topBranchB = buildGitGraph(branchB);

            //TODO take last commits and check if there are another branches
            storage.getRepositoryGraph().setTopCommit(branchA, topBranchA);
            storage.getRepositoryGraph().setTopCommit(branchB, topBranchB);

            depthFastSearch(topBranchB, this::handleCommitAsPreStored);
        }
    }

    private void handleBranchesIfAtAtLeastOneIsCached(String branchA, String topBranchA,
                                                      String branchB, String topBranchB) throws IOException {
        if (topBranchB != null && topBranchA == null) {
            branchB = branchA;

            topBranchA = topBranchB;
            topBranchB = null;
        }

        if (topBranchA == null)
            return;

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
        //TODO if exists log off
        return this::addCommitInPreStored;
    }

    private Function<String, Function> handleCommitAsPreStored(String commit) {
        if (storage.getPreStoredBranch().contains(commit)) {
            if(!storage.getCommitsUnderLastCommon().contains(commit)) {
                storage.getLastCommonCommits().add(commit);
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
