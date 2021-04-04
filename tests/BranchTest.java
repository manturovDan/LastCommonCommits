import lastCommonCommitsGitHub.HTTPInteraction.HTTPGitHub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BranchTest {
    @Test
    public void createRequestTest() {
        HTTPGitHub interaction = new HTTPGitHub("manturovDanExperimental", "cross3", "");
        System.out.println(interaction.branch("A"));

        Assertions.assertTrue(true);
    }
}
