package lastCommonCommitsGitHub;

import lastCommonCommitsGitHub.UI.IOInteraction;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class RepoInteraction {
    public void interact(InputStream is, PrintStream ps) throws IOException {
        IOInteraction ui = new IOInteraction(is, ps);
        ui.findOutGitHubRepositoryInfo();
    }
}
