import lastCommonCommitsGitHub.RepoInteraction;
import lastCommonCommitsGitHub.UI.IOInteraction;

import java.io.IOException;

public class Run {
    public static void main(String[] args) throws IOException {
        RepoInteraction interaction = new RepoInteraction();
        interaction.interact(System.in, System.out);
    }
}
