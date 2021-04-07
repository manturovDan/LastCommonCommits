package lastCommonCommitsGitHub;

import lastCommonCommitsGitHub.UI.IOInteraction;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinder;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinderFactory;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinderFactoryGitHub;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RepoInteraction {
    private final IOInteraction ui;
    private final InputStream is;
    private final PrintStream ps;

    public RepoInteraction(InputStream is, PrintStream ps) {
        this.is = is;
        this.ps = ps;
        ui = new IOInteraction(is, ps);
    }

    public void interact() {
        try (Scanner scanner = new Scanner(is)) {
            LastCommonCommitsFinder finder;
            finder = retrieveFinder(scanner);
            if (finder == null) {
                ps.println("Cancel");
                return;
            }

            retrieveBranches(scanner);
            ps.println("Exit");
        } catch (NoSuchElementException e) {
            ps.println("Input error was occurred, try to restart program with correct Input Stream\nMore details:");
            e.printStackTrace(ps);
        }
    }

    private LastCommonCommitsFinder retrieveFinder(Scanner scanner) {
        boolean getRepoSuccess = ui.findOutGitHubRepositoryInfo(scanner);
        if (!getRepoSuccess)
            return null;

        LastCommonCommitsFinderFactory factory = new LastCommonCommitsFinderFactoryGitHub();
        return factory.create(ui.getOwner(), ui.getRepo(), ui.getToken());
    }

    private void retrieveBranches(Scanner scanner) {
        while (true) {
            boolean shouldContinueSearching = ui.findOutBranchesName(scanner);
            if (!shouldContinueSearching)
                return;


        }
    }
}
