import lastCommonCommitsGitHub.HTTPInteraction.HTTPGitHub;
import lastCommonCommitsGitHub.finder.search.DepthFirstSearchInRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class HTTPTest {
    /*@Test
    public void createRequestTest() {
        HTTPGitHub interaction = new HTTPGitHub("manturovDanExperimental", "cross3", "");
        Assertions.assertEquals("84e222f75a5b37b63602abcb2b46f9984093d3d7", interaction.branch("A"));
    }*/

    @Test
    public void lastEventTest() throws IOException {
        HTTPGitHub interaction = new HTTPGitHub("manturovDanExperimental", "cross3", "");
        //datetime of push + 90 days
        if (1617531792L + 90*24*60*60 > (System.currentTimeMillis() / 1000L))
            Assertions.assertEquals(15808095406L, interaction.lastEvent());
        else
            Assertions.assertEquals(0L, interaction.lastEvent());
    }

    @Test
    public void lastEventTestOld() throws IOException {
        HTTPGitHub interaction = new HTTPGitHub("manturovDan", "Sake", "");
        Assertions.assertEquals(0L, interaction.lastEvent());
    }

    //@Test
    public void allCommits() throws IOException {
        HTTPGitHub interaction = new HTTPGitHub("manturovDanExperimental", "cross3", "");
        DepthFirstSearchInRepo dfs = new DepthFirstSearchInRepo(interaction);
        dfs.buildGitGraph("B");
    }
}
