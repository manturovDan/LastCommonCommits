package lastCommonCommitsGitHub.finder.storage;

import java.util.AbstractMap;
import java.util.List;

public class SearchStorage {
    private final RepositoryGraph repositoryGraph;
    private final SetOfCommits preStoredBranch;
    private final SetOfCommits commitsUnderLastCommon;
    private final SetOfCommits lastCommonCommits;
    private final DFSStack dfsStack;
    private final String repo;

    public SearchStorage(String repo) {
        this.repo = repo;
        repositoryGraph = new RepositoryGraph(repo);
        preStoredBranch = new SetOfCommits();
        commitsUnderLastCommon = new SetOfCommits();
        lastCommonCommits = new SetOfCommits();
        dfsStack = new DFSStack();
    }

    public void addCommitInRepo(AbstractMap.SimpleEntry<String, List<String>> pairCommitParents) {
        repositoryGraph.put(pairCommitParents.getKey(), pairCommitParents.getValue());
    }

    public void copyCommitsFromGraphToPreStoredBranch() {
        preStoredBranch.establishSet(repositoryGraph.keys());
    }

    public String presentRepoGraph() {
        return repositoryGraph.toString();
    }

    public DFSStack getDfsStack() {
        return dfsStack;
    }

    public RepositoryGraph getRepositoryGraph() {
        return repositoryGraph;
    }
}
