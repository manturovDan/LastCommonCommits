package lastCommonCommitsGitHub.finder.storage;

import java.util.AbstractMap;
import java.util.List;

public class SearchStorage {
    private final RepositoryGraph repositoryGraph;
    private final SetOfCommits preStoredBranch;
    private final SetOfCommits commitsUnderLastCommon;
    private final SetOfCommits lastCommonCommits;
    private final SetOfCommits commitsPreliminarilyUnderLastCommon;
    private final DFSStack dfsStack;
    private final String repo;
    private final long lastEvent;

    public SearchStorage(String repo, long lastEvent) {
        this.repo = repo;
        this.lastEvent = lastEvent;
        repositoryGraph = new RepositoryGraph();
        preStoredBranch = new SetOfCommits();
        commitsUnderLastCommon = new SetOfCommits();
        lastCommonCommits = new SetOfCommits();
        commitsPreliminarilyUnderLastCommon = new SetOfCommits();
        dfsStack = new DFSStack();
    }

    public void addCommitInRepo(AbstractMap.SimpleEntry<String, List<String>> pairCommitParents) {
        repositoryGraph.put(pairCommitParents.getKey(), pairCommitParents.getValue());
    }

    public DFSStack getDfsStack() {
        return dfsStack;
    }

    public RepositoryGraph getRepositoryGraph() {
        return repositoryGraph;
    }

    public SetOfCommits getPreStoredBranch() {
        return preStoredBranch;
    }

    public SetOfCommits getLastCommonCommits() {
        return lastCommonCommits;
    }

    public SetOfCommits getCommitsUnderLastCommon() {
        return commitsUnderLastCommon;
    }

    public SetOfCommits getCommitsPreliminarilyUnderLastCommon() {
        return commitsPreliminarilyUnderLastCommon;
    }

    public long getLastEvent() {
        return lastEvent;
    }

    public String getRepo() {
        return repo;
    }

    public void clearIntermediate() {
        preStoredBranch.clear();
        commitsPreliminarilyUnderLastCommon.clear();
        commitsUnderLastCommon.clear();
        lastCommonCommits.clear();
    }
}
