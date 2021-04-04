import HTTPInteraction.HTTPGitHubMediatorHub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BranchTest {
    @Test
    public void createRequestTest() {
        HTTPGitHubMediatorHub interaction = new HTTPGitHubMediatorHub("manturovDanExperimental", "cross3", "");
        System.out.println(interaction.branch("A"));

        Assertions.assertTrue(true);
    }
}
