package lastCommonCommitsGitHub.finder.storage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class RepositoryGraph {
    private final String repo;
    private long lastEventId;
    private final HashMap<String, List<String>> commitGraph;

    public RepositoryGraph(String repo) {
        this.repo = repo;
        this.lastEventId = 0;
        commitGraph = new HashMap<>();
    }

    void put(String commit, List<String> parents) {
        commitGraph.put(commit, parents);
    }

    HashSet<String> keys() {
        return new HashSet<>(commitGraph.keySet());
    }

    public List<String> getParents(String commit) {
        return commitGraph.get(commit);
    }

    @Override
    public String toString() {
        return commitGraph.toString();
    }
}
