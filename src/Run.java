import lastCommonCommitsGitHub.RepoInteraction;

import java.io.IOException;

public class Run {
    public static void main(String[] args) throws IOException {
        RepoInteraction interaction = new RepoInteraction(System.in, System.out);
        interaction.interact();
    }
}
