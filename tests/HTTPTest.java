import lastCommonCommitsGitHub.HTTPInteraction.HTTPGitHub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HTTPTest {
    @Test
    public void createRequestTest() {
        HTTPGitHub interaction = new HTTPGitHub("manturovDanExperimental", "cross3", "");
        System.out.println(interaction.branch("A"));

        Assertions.assertTrue(true);
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
}
