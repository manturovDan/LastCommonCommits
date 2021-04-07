import lastCommonCommitsGitHub.RepoInteraction;

public class Run {
    public static void main(String[] args) {
        RepoInteraction interaction = new RepoInteraction(System.in, System.out);
        interaction.interact();
    }
}
