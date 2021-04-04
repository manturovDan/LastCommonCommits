package lastCommonCommitsGitHub;

import lastCommonCommitsGitHub.UI.IOInteraction;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinder;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinderFactory;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinderFactoryGitHub;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinderGitHub;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class RepoInteraction {
    public void interact(InputStream is, PrintStream ps) throws IOException {
        IOInteraction ui = new IOInteraction(is, ps);
        ui.findOutGitHubRepositoryInfo();

        LastCommonCommitsFinderFactory factory = new LastCommonCommitsFinderFactoryGitHub();
        LastCommonCommitsFinder finder = factory.create(ui.getOwner(), ui.getRepo(), ui.getToken());


    }
}
