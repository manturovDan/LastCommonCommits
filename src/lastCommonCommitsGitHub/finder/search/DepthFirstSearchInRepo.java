package lastCommonCommitsGitHub.finder.search;

import lastCommonCommitsGitHub.HTTPInteraction.HTTPGitHub;
import lastCommonCommitsGitHub.HTTPInteraction.JSONHandler;
import lastCommonCommitsGitHub.finder.storage.SearchStorage;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;

/**
 * It is core class for last common commit(-s) search
 * sequencing:
 * 1) receiving the first branch graph and putting it into storage.repositoryGraph (it can be saved previously, in this case do nothing)
 * 2) putting all sha-s of the first branch in storage.preStoredBranch
 * 3) receiving the second branch graph and putting it into the same storage.repositoryGraph
 *      (it can be saver previously, in this case do nothing, this graph can also contain saved other branches)
 * 4) DFS #1 (without marking commits because we haven't cycles in commit-tree) the second branch. When we meet commit:
 *      * does storage.preStoredBranch contain this commit?:
 *          YES: does storage.underLastCommonCommits (commit that are under commits that are considered now as last-common) contain this commit
 *              NO: put this commit into storage.lastCommonCommits - result collection,
 *                  put into storage.dfsStack (it is stack for DFS to avoid recursion) List of current commit's parents
 *                  put into storage.commitsPreliminarilyUnderLastCommon current commit's parents (marking parents as commits, that are under last common commits)
 *                  DFS #2 to next commit from stack with other action:
 *                      * does storage.underLastCommonCommits contain this commit?
 *                          NO: add commit into storage.underLastCommonCommits and delete from storage.lastCommonCommits, because this commit is common, but not last
 *                              put into storage.dfsStack  List of current commit's parents
 *                              put into storage.commitsPreliminarilyUnderLastCommon current commit's parents
 *                          if next commit from the stack in marked (storage.commitsPreliminarilyUnderLastCommon) continue with DFS#2, else with DFS#1
 *
 *          NO: put into storage.dfsStack (it is stack for DFS to avoid recursion) List of current commit's parents
 * 5) clear all intermediate data: storage.preStoredBranch, storage.commitsPreliminarilyUnderLastCommon, storage.commitsUnderLastCommon, storage.lastCommonCommits
 */

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
            if (topCommit == null) {
                topCommit = commit.getKey();
                if (storage.getRepositoryGraph().containsCommitInGraph(topCommit))
                    break;
            }

            storage.addCommitInRepo(commit);
        }

        return topCommit;
    }

    public Collection<String> lastCommonCommits(String branchA, String branchB) throws IOException {
        long lastEventIdInStorage = storage.getLastEvent();
        long lastEventIdInRemote = HTTPInteraction.lastEvent();
        if (lastEventIdInRemote != lastEventIdInStorage) {
            storage.getRepositoryGraph().clear();
            storage = new SearchStorage(HTTPInteraction.getRepo(), lastEventIdInRemote);
        }

        String topBranchA = storage.getRepositoryGraph().getTopCommit(branchA);
        String topBranchB = storage.getRepositoryGraph().getTopCommit(branchB);

        handleBranchesIfAtLeastOneIsCached(branchA, topBranchA, branchB, topBranchB);
        handleBranchesIfBothArentCached(branchA, topBranchA, branchB, topBranchB);

        Collection<String> lastCommonCommits = storage.getLastCommonCommits().getList();

        clearIntermediateData();

        return lastCommonCommits;
    }

    private void clearIntermediateData() {
        storage.clearIntermediate();
    }

    private void handleBranchesIfBothArentCached(String branchA, String topBranchA,
                                                 String branchB, String topBranchB) throws IOException {
        if (topBranchA == null && topBranchB == null) {
            topBranchA = buildGitGraph(branchA);
            depthFastSearch(topBranchA, this::addCommitInPreStored);
            topBranchB = buildGitGraph(branchB);

            storage.getRepositoryGraph().setTopCommit(branchA, topBranchA);
            storage.getRepositoryGraph().setTopCommit(branchB, topBranchB);

            depthFastSearch(topBranchB, this::handleCommitAsPotentiallyPreStored);
        }
    }

    private void handleBranchesIfAtLeastOneIsCached(String branchA, String topBranchA,
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
            storage.getRepositoryGraph().setTopCommit(branchB, topBranchB);
        }

        depthFastSearch(topBranchB, this::handleCommitAsPotentiallyPreStored);

    }

    private void searchDeeper(DFSAction action) {
        while (!storage.getDfsStack().isEmpty()) {
            String currentCommit = storage.getDfsStack().pop();
            action = action.apply(currentCommit);
        }
    }

    private DFSAction addCommitInPreStored(String commit) {
        storage.getPreStoredBranch().add(commit);
        pushCommitsListInStack(storage.getRepositoryGraph().getParents(commit));
        return this::addCommitInPreStored;
    }

    private DFSAction handleCommitAsPotentiallyPreStored(String commit) {
        if (storage.getPreStoredBranch().contains(commit)) {
            if(!storage.getCommitsUnderLastCommon().contains(commit)) {
                storage.getLastCommonCommits().add(commit);
                List<String> parents = storage.getRepositoryGraph().getParents(commit);
                pushCommitsListInStack(parents);
                markCommitsAsPreliminarilyUnderLastCommonCommits(parents);
                return this::handleCommitAsCommon;
            }
        }
        else {
            pushCommitsListInStack(storage.getRepositoryGraph().getParents(commit));
        }

        return this::handleCommitAsPotentiallyPreStored;
    }

    private DFSAction handleCommitAsCommon(String commit) {
        if (!storage.getCommitsUnderLastCommon().contains(commit)) {
            storage.getCommitsUnderLastCommon().add(commit);
            storage.getLastCommonCommits().remove(commit);

            List<String> parents = storage.getRepositoryGraph().getParents(commit);
            if (parents.size() > 0) {
                pushCommitsListInStack(parents);
                markCommitsAsPreliminarilyUnderLastCommonCommits(parents);
                return this::handleCommitAsCommon;
            }
        }

        if (storage.getDfsStack().isEmpty() ||
                storage.getCommitsPreliminarilyUnderLastCommon().contains(storage.getDfsStack().getTop()))
            return this::handleCommitAsCommon;
        return this::handleCommitAsPotentiallyPreStored;
    }

    private void depthFastSearch(String topCommit, DFSAction action) {
        storage.getDfsStack().push(topCommit);
        searchDeeper(action);
    }

    private void pushCommitsListInStack(List<String> commitsList) {
        for (String commit : commitsList) {
            storage.getDfsStack().push(commit);
        }
    }

    private void markCommitsAsPreliminarilyUnderLastCommonCommits(List<String> commitsPreliminarilyUnderLastCommon) {
        for (String commit : commitsPreliminarilyUnderLastCommon) {
            storage.getCommitsPreliminarilyUnderLastCommon().add(commit);
        }
    }

    public long getLastEventId() {
        return storage.getLastEvent();
    }
}
