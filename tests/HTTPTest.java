import lastCommonCommitsGitHub.HTTPInteraction.HTTPGitHub;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinder;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinderFactory;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinderFactoryGitHub;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinderGitHub;
import lastCommonCommitsGitHub.finder.search.DepthFirstSearchInRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Scanner;

public class HTTPTest {
    @Test
    public void lastEventTest() throws IOException {
        HTTPGitHub interaction = new HTTPGitHub("manturovDanExperimental", "cross3", AlgorithmTests.token);
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

    @Test
    public void emptyRepo() {
        LastCommonCommitsFinderFactory factory = new LastCommonCommitsFinderFactoryGitHub();
        LastCommonCommitsFinder finder = factory.create("manturovDanExperimental", "emptyRepo", AlgorithmTests.token);
        Assertions.assertThrows(IOException.class, () -> finder.findLastCommonCommits("A", "B"));
    }

}
