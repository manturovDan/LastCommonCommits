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

    public void cross3BCCommonCommits(HashMap<String, List<String>> graph) throws Exception {
        Assertions.assertEquals(graph.get("3aab5282d567ed69daaec373e7cc5bc1985015ef").size(), 3);
        Assertions.assertTrue(graph.get("3aab5282d567ed69daaec373e7cc5bc1985015ef")
                .contains("615ba764d7ce72e496324b28dab880a6fed56455"));
        Assertions.assertTrue(graph.get("3aab5282d567ed69daaec373e7cc5bc1985015ef")
                .contains("55242422e68e4b9f817e3fe9702e2fa49859c2cf"));
        Assertions.assertTrue(graph.get("3aab5282d567ed69daaec373e7cc5bc1985015ef")
                .contains("d9ac181925ef186b66efa4a82ba73e88ea3bc98a"));

        Assertions.assertEquals(graph.get("18944685cb9f413ee3e52cdb6db02559c6fdcccd").size(), 0);

        Assertions.assertEquals(graph.get("615ba764d7ce72e496324b28dab880a6fed56455").size(), 1);
        Assertions.assertTrue(graph.get("615ba764d7ce72e496324b28dab880a6fed56455")
                .contains("18944685cb9f413ee3e52cdb6db02559c6fdcccd"));

        Assertions.assertEquals(graph.get("55242422e68e4b9f817e3fe9702e2fa49859c2cf").size(), 1);
        Assertions.assertTrue(graph.get("55242422e68e4b9f817e3fe9702e2fa49859c2cf")
                .contains("18944685cb9f413ee3e52cdb6db02559c6fdcccd"));

        Assertions.assertEquals(graph.get("d9ac181925ef186b66efa4a82ba73e88ea3bc98a").size(), 1);
        Assertions.assertTrue(graph.get("d9ac181925ef186b66efa4a82ba73e88ea3bc98a")
                .contains("18944685cb9f413ee3e52cdb6db02559c6fdcccd"));
    }

    public void cross3BranchBCTest(String branchBC) throws Exception {
        HTTPGitHub interaction = new HTTPGitHub("manturovDanExperimental", "cross3", "");
        DeepFirstSearchInRepo dfs = new DeepFirstSearchInRepo(interaction);
        dfs.buildGitGraph(branchBC);
        SearchStorage storage = getStorageFromDFS(dfs);
        HashMap<String, List<String>> graph = getGraphFromStorage(storage);

        Assertions.assertEquals(graph.size(), 5);

        cross3BCCommonCommits(graph);
    }

    @Test
    public void cross3BranchBTest() throws Exception {
        cross3BranchBCTest("B");
    }

    @Test
    public void cross3BranchCTest() throws Exception {
        cross3BranchBCTest("C");
    }

    @Test
    public void cross3BranchATest() throws Exception {
        HTTPGitHub interaction = new HTTPGitHub("manturovDanExperimental", "cross3", "");
        DeepFirstSearchInRepo dfs = new DeepFirstSearchInRepo(interaction);
        dfs.buildGitGraph("A");
        SearchStorage storage = getStorageFromDFS(dfs);
        HashMap<String, List<String>> graph = getGraphFromStorage(storage);

        Assertions.assertEquals(graph.size(), 5);

        Assertions.assertEquals(graph.get("84e222f75a5b37b63602abcb2b46f9984093d3d7").size(), 3);
        Assertions.assertTrue(graph.get("84e222f75a5b37b63602abcb2b46f9984093d3d7")
                .contains("615ba764d7ce72e496324b28dab880a6fed56455"));
        Assertions.assertTrue(graph.get("84e222f75a5b37b63602abcb2b46f9984093d3d7")
                .contains("55242422e68e4b9f817e3fe9702e2fa49859c2cf"));
        Assertions.assertTrue(graph.get("84e222f75a5b37b63602abcb2b46f9984093d3d7")
                .contains("d9ac181925ef186b66efa4a82ba73e88ea3bc98a"));

        Assertions.assertEquals(graph.get("18944685cb9f413ee3e52cdb6db02559c6fdcccd").size(), 0);

        Assertions.assertEquals(graph.get("615ba764d7ce72e496324b28dab880a6fed56455").size(), 1);
        Assertions.assertTrue(graph.get("615ba764d7ce72e496324b28dab880a6fed56455")
                .contains("18944685cb9f413ee3e52cdb6db02559c6fdcccd"));

        Assertions.assertEquals(graph.get("55242422e68e4b9f817e3fe9702e2fa49859c2cf").size(), 1);
        Assertions.assertTrue(graph.get("55242422e68e4b9f817e3fe9702e2fa49859c2cf")
                .contains("18944685cb9f413ee3e52cdb6db02559c6fdcccd"));

        Assertions.assertEquals(graph.get("d9ac181925ef186b66efa4a82ba73e88ea3bc98a").size(), 1);
        Assertions.assertTrue(graph.get("d9ac181925ef186b66efa4a82ba73e88ea3bc98a")
                .contains("18944685cb9f413ee3e52cdb6db02559c6fdcccd"));
    }

    @Test
    public void cross3BranchMasterTest() throws Exception {
        HTTPGitHub interaction = new HTTPGitHub("manturovDanExperimental", "cross3", "");
        DeepFirstSearchInRepo dfs = new DeepFirstSearchInRepo(interaction);
        dfs.buildGitGraph("master");
        SearchStorage storage = getStorageFromDFS(dfs);
        HashMap<String, List<String>> graph = getGraphFromStorage(storage);

        Assertions.assertEquals(graph.size(), 1);

        Assertions.assertEquals(graph.get("18944685cb9f413ee3e52cdb6db02559c6fdcccd").size(), 0);
    }

    @Test
    public void cross3FullRepo() throws Exception {
        HTTPGitHub interaction = new HTTPGitHub("manturovDanExperimental", "cross3", "");
        DeepFirstSearchInRepo dfs = new DeepFirstSearchInRepo(interaction);
        dfs.buildGitGraph("A");
        dfs.buildGitGraph("B");

        SearchStorage storage = getStorageFromDFS(dfs);
        HashMap<String, List<String>> graph = getGraphFromStorage(storage);

        Assertions.assertEquals(graph.size(), 6);

        cross3BCCommonCommits(graph);

        Assertions.assertEquals(graph.get("84e222f75a5b37b63602abcb2b46f9984093d3d7").size(), 3);
        Assertions.assertTrue(graph.get("84e222f75a5b37b63602abcb2b46f9984093d3d7")
                .contains("615ba764d7ce72e496324b28dab880a6fed56455"));
        Assertions.assertTrue(graph.get("84e222f75a5b37b63602abcb2b46f9984093d3d7")
                .contains("55242422e68e4b9f817e3fe9702e2fa49859c2cf"));
        Assertions.assertTrue(graph.get("84e222f75a5b37b63602abcb2b46f9984093d3d7")
                .contains("d9ac181925ef186b66efa4a82ba73e88ea3bc98a"));
    }
}