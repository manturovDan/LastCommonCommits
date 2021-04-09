import lastCommonCommitsGitHub.HTTPInteraction.HTTPGitHub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
        HTTPGitHub interaction = new HTTPGitHub("manturovDan", "Sake", AlgorithmTests.token);
        Assertions.assertEquals(0L, interaction.lastEvent());
    }
}
