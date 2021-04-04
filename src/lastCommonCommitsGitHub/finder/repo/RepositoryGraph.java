package lastCommonCommitsGitHub.finder.repo;

import java.util.HashSet;

public class RepositoryGraph {
    private String repo;
    private long lastEventId;
    private HashSet<Commit> commitGraph;
}
