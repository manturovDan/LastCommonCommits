import lastCommonCommitsGitHub.UI.IOInteraction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

public class InteractionTest {
    @Test
    public void getRepoInfo() throws Exception {
        InputStream is = new FileInputStream("tests/testResources/inputRepo1.txt");
        IOInteraction ui = new IOInteraction(is, new PrintStream(
                new OutputStream() {
                    @Override
                    public void write(int b) throws IOException {}
                }
        ));

        ui.findOutGitHubRepositoryInfo(new Scanner(is));
        Assertions.assertEquals("OwnerName", ui.getOwner());
        Assertions.assertNotEquals("OwnerName1", ui.getOwner());
        Assertions.assertEquals("RepositoryName", ui.getRepo());
        Assertions.assertEquals("TokenValue", ui.getToken());
    }


    @Test
    public void getRepoInfoErr() throws Exception {
        InputStream is = new FileInputStream("tests/testResources/inputRepo1.txt");
        IOInteraction ui = new IOInteraction(is, new PrintStream(
                new OutputStream() {
                    @Override
                    public void write(int b) throws IOException {}
                }
        ));

        Assertions.assertThrows(RuntimeException.class, ui::getOwner);

        ui.findOutGitHubRepositoryInfo(new Scanner(is));
        Assertions.assertEquals("OwnerName", ui.getOwner());
        Assertions.assertNotEquals("OwnerName1", ui.getOwner());
        Assertions.assertEquals("RepositoryName", ui.getRepo());
        Assertions.assertEquals("TokenValue", ui.getToken());
    }

    @Test
    public void emptyTokenTest() throws Exception {
        InputStream is = new FileInputStream("tests/testResources/inputRepoEmpToken.txt");
        IOInteraction ui = new IOInteraction(is, new PrintStream(
                new OutputStream() {
                    @Override
                    public void write(int b) throws IOException {}
                }
        ));

        ui.findOutGitHubRepositoryInfo(new Scanner(is));
        Assertions.assertEquals("OwnerName", ui.getOwner());
        Assertions.assertNotEquals("OwnerName1", ui.getOwner());
        Assertions.assertEquals("RepositoryName", ui.getRepo());
        Assertions.assertEquals("", ui.getToken());
    }
}
