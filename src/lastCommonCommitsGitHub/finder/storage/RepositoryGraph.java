package lastCommonCommitsGitHub.finder.storage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class RepositoryGraph {
    private final HashMap<String, List<String>> commitGraph;
    private final HashMap<String, String> branchTopCommit;

    public RepositoryGraph() {
        commitGraph = new HashMap<>();
        branchTopCommit = new HashMap<>();
    }

    void put(String commit, List<String> parents) {
        commitGraph.put(commit, parents);
    }

    public List<String> getParents(String commit) {
        return commitGraph.get(commit);
    }

    public void setTopCommit(String branch, String commit) {
        branchTopCommit.put(branch, commit);
    }

    public String getTopCommit(String branch) {
        return branchTopCommit.get(branch);
    }

    @Override
    public String toString() {
        return commitGraph.toString();
    }
}
