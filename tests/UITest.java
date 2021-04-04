import lastCommonCommitsGitHub.UI.IOInteraction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

public class UITest {
    @Test
    public void getRepoInfo() throws Exception {
        IOInteraction ui = new IOInteraction(new FileInputStream("testResources/inputRepo1.txt"), new PrintStream(
                new OutputStream() {
                    @Override
                    public void write(int b) throws IOException {}
                }
        ));

        ui.findOutGitHubRepositoryInfo();
        Assertions.assertEquals("OwnerName", ui.getOwner());
        Assertions.assertNotEquals("OwnerName1", ui.getOwner());
        Assertions.assertEquals("RepositoryName", ui.getRepo());
        Assertions.assertEquals("TokenValue", ui.getToken());
    }


    @Test
    public void getRepoInfoErr() throws Exception {
        IOInteraction ui = new IOInteraction(new FileInputStream("testResources/inputRepo1.txt"), new PrintStream(
                new OutputStream() {
                    @Override
                    public void write(int b) throws IOException {}
                }
        ));

        Assertions.assertThrows(RuntimeException.class, ui::getOwner);

        ui.findOutGitHubRepositoryInfo();
        Assertions.assertEquals("OwnerName", ui.getOwner());
        Assertions.assertNotEquals("OwnerName1", ui.getOwner());
        Assertions.assertEquals("RepositoryName", ui.getRepo());
        Assertions.assertEquals("TokenValue", ui.getToken());
    }

    @Test
    public void emptyTokenTest() throws Exception {
        IOInteraction ui = new IOInteraction(new FileInputStream("testResources/inputRepoEmpToken.txt"), new PrintStream(
                new OutputStream() {
                    @Override
                    public void write(int b) throws IOException {}
                }
        ));

        ui.findOutGitHubRepositoryInfo();
        Assertions.assertEquals("OwnerName", ui.getOwner());
        Assertions.assertNotEquals("OwnerName1", ui.getOwner());
        Assertions.assertEquals("RepositoryName", ui.getRepo());
        Assertions.assertEquals("", ui.getToken());
    }
}
