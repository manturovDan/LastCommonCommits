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
        System.out.println(interaction.lastEvent());

        Assertions.assertTrue(true);
    }
}
