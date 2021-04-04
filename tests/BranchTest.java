import HTTPInteraction.HTTPIntercationGitHub.HTTPGitHub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BranchTest {
    @Test
    public void createRequestTest() {
        HTTPGitHub interaction = new HTTPGitHub("manturovDan", "BHW", "");
        System.out.println(interaction.branch("kPath"));

        Assertions.assertTrue(true);
    }
}
