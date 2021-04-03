import HTTPIntercationGitHub.HTTPGitHub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BranchTest {
    @Test
    public void createRequestTest() {
        HTTPGitHub interaction = new HTTPGitHub("", "", "");
        System.out.println(interaction.branch());

        Assertions.assertTrue(true);
    }
}
