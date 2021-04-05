import lastCommonCommitsGitHub.HTTPInteraction.HTTPGitHub;
import lastCommonCommitsGitHub.finder.search.DeepFirstSearchInRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HTTPTest {
    @Test
    public void createRequestTest() {
        HTTPGitHub interaction = new HTTPGitHub("manturovDanExperimental", "cross3", "");
        Assertions.assertEquals("84e222f75a5b37b63602abcb2b46f9984093d3d7", interaction.branch("A"));
    }

    @Test
    public void lastEventTest() {
        HTTPGitHub interaction = new HTTPGitHub("manturovDanExperimental", "cross3", "");
        //datetime of push + 90 days
        if (1617531792L + 90*24*60*60 > (System.currentTimeMillis() / 1000L))
            Assertions.assertEquals(15808095406L, interaction.lastEvent());
        else
            Assertions.assertEquals(0L, interaction.lastEvent());
    }

    @Test
    public void lastEventTestOld() {
        HTTPGitHub interaction = new HTTPGitHub("manturovDan", "Sake", "");
        Assertions.assertEquals(0L, interaction.lastEvent());
    }

    //@Test
    public void allCommits() {
        HTTPGitHub interaction = new HTTPGitHub("manturovDanExperimental", "cross3", "");
        DeepFirstSearchInRepo dfs = new DeepFirstSearchInRepo(interaction);
        dfs.buildGitGraph("B");
    }
}
