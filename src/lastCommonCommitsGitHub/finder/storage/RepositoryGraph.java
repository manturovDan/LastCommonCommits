package lastCommonCommitsGitHub.finder.storage;

import java.util.HashMap;
import java.util.List;

public class RepositoryGraph {
    private String repo;
    private long lastEventId;
    private HashMap<String, List<String>> commitGraph;
}
