import lastCommonCommitsGitHub.HTTPInteraction.HTTPGitHub;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinder;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinderFactory;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinderFactoryGitHub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class HTTPTest {
    @Test
    public void lastEventTest() throws IOException {
        HTTPGitHub interaction = new HTTPGitHub("manturovDanExperimental", "cross3", OAuthToken.token);
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
        LastCommonCommitsFinder finder = factory.create("manturovDanExperimental", "emptyRepo", OAuthToken.token);
        Assertions.assertThrows(IOException.class, () -> finder.findLastCommonCommits("A", "B"));
    }

}
