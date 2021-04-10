import lastCommonCommitsGitHub.finder.LastCommonCommitsFinder;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinderFactory;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinderFactoryGitHub;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinderGitHub;
import lastCommonCommitsGitHub.finder.search.DepthFirstSearchInRepo;
import lastCommonCommitsGitHub.finder.storage.RepositoryGraph;
import lastCommonCommitsGitHub.finder.storage.SearchStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class CachingTest {

    @SuppressWarnings("unchecked")
    @Test
    public void checkCaching() throws IOException, NoSuchFieldException, IllegalAccessException {
        LastCommonCommitsFinderFactory factory = new LastCommonCommitsFinderFactoryGitHub();
        LastCommonCommitsFinderGitHub finder1 = (LastCommonCommitsFinderGitHub) factory
                .create("manturovDanExperimental", "commonInitial", AlgorithmTests.token);
        Collection<String> common = finder1.findLastCommonCommits("branch5", "master");
        Assertions.assertEquals(common.size(), 1);
        Assertions.assertTrue(common.contains("57aa3a438f0449973dc7a3c2f87ecabf93cca9b4"));

        Field DFSField = LastCommonCommitsFinderGitHub.class.getDeclaredField("search");
        DFSField.setAccessible(true);
        DepthFirstSearchInRepo search = (DepthFirstSearchInRepo) DFSField.get(finder1);

        Field storageField = DepthFirstSearchInRepo.class.getDeclaredField("storage");
        storageField.setAccessible(true);
        SearchStorage storage = (SearchStorage) storageField.get(search);

        Field commitGraphField = RepositoryGraph.class.getDeclaredField("commitGraph");
        commitGraphField.setAccessible(true);
        HashMap<String, List<String>> graph = (HashMap<String, List<String>>) commitGraphField.get(storage.getRepositoryGraph());

        Field branchesTopField = RepositoryGraph.class.getDeclaredField("branchTopCommit");
        branchesTopField.setAccessible(true);
        HashMap<String, String> branchTopCommit = (HashMap<String, String>) branchesTopField.get(storage.getRepositoryGraph());

        Assertions.assertEquals(9, graph.size());
        Assertions.assertEquals(2, branchTopCommit.size());

        Assertions.assertEquals(branchTopCommit.get("master"), "7ad9a998ead5955b3cc4f512e5fa5c0e69e58b98");
        Assertions.assertEquals(branchTopCommit.get("branch5"), "98736d3d2aeffeafc16e26db1044d198190e203b");

        LastCommonCommitsFinderGitHub finder2 = (LastCommonCommitsFinderGitHub) factory
                .create("manturovDanExperimental", "commonInitial", AlgorithmTests.token);

        Assertions.assertEquals(finder1, finder2);

        checkAllIntermediateDataWasCleared(storage);

        Collection<String> common35 = finder1.findLastCommonCommits("branch5", "branch3");
        Assertions.assertEquals(common35.size(), 2);
        Assertions.assertTrue(common35.contains("897f12602df145b72da060c739ca0d732c1b6346"));
        Assertions.assertTrue(common35.contains("f44a627a095ef81374e0c883768c8bc23307e031"));

        Assertions.assertEquals(11, graph.size());
        Assertions.assertEquals(3, branchTopCommit.size());

        Assertions.assertEquals(branchTopCommit.get("master"), "7ad9a998ead5955b3cc4f512e5fa5c0e69e58b98");
        Assertions.assertEquals(branchTopCommit.get("branch5"), "98736d3d2aeffeafc16e26db1044d198190e203b");
        Assertions.assertEquals(branchTopCommit.get("branch3"), "ffa3735a0bdc912e4eb909959faac5f83316c8ea");

    }

    private void checkAllIntermediateDataWasCleared(SearchStorage storage) {
        Assertions.assertTrue(storage.getDfsStack().isEmpty());
        Assertions.assertEquals(storage.getCommitsPreliminarilyUnderLastCommon().size(), 0);
        Assertions.assertEquals(storage.getCommitsUnderLastCommon().size(), 0);
        Assertions.assertEquals(storage.getPreStoredBranch().size(), 0);
        Assertions.assertEquals(storage.getLastCommonCommits().size(), 0);
        Assertions.assertEquals("commonInitial", storage.getRepo());
    }
}
