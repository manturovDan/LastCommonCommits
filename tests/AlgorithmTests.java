import lastCommonCommitsGitHub.HTTPInteraction.HTTPGitHub;
import lastCommonCommitsGitHub.finder.search.DeepFirstSearchInRepo;
import lastCommonCommitsGitHub.finder.storage.RepositoryGraph;
import lastCommonCommitsGitHub.finder.storage.SearchStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

public class AlgorithmTests {
    private SearchStorage getStorageFromDFS(DeepFirstSearchInRepo dfs) throws Exception {
        Field storageField = DeepFirstSearchInRepo.class.getDeclaredField("storage");
        storageField.setAccessible(true);

        return (SearchStorage) storageField.get(dfs);
    }

    @SuppressWarnings("unchecked")
    private HashMap<String, List<String>> getGraphFromStorage(SearchStorage storage) throws Exception {
        Field graphField = SearchStorage.class.getDeclaredField("repositoryGraph");
        graphField.setAccessible(true);
        RepositoryGraph graph = (RepositoryGraph) graphField.get(storage);
        
        Field commitGraphField = RepositoryGraph.class.getDeclaredField("commitGraph");
        commitGraphField.setAccessible(true);
        return (HashMap<String, List<String>>) commitGraphField.get(graph);
    }

    @Test
    public void allCommits() throws Exception {
        HTTPGitHub interaction = new HTTPGitHub("manturovDanExperimental", "cross3", "");
        DeepFirstSearchInRepo dfs = new DeepFirstSearchInRepo(interaction);
        dfs.buildGitGraph("B");
        SearchStorage storage = getStorageFromDFS(dfs);
        HashMap<String, List<String>> graph = getGraphFromStorage(storage);

        Assertions.assertEquals(graph.size(), 5);
    }
}
