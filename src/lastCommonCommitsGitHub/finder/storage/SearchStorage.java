package lastCommonCommitsGitHub.finder.storage;

public class SearchStorage {
    private RepositoryGraph repositoryGraph;
    private SetOfCommits preStoredBranch;
    private SetOfCommits commitsUnderLastCommon;
    private SetOfCommits lastCommonCommits;
    private String repo;

    public SearchStorage(String repo) {
        this.repo = repo;
        repositoryGraph = new RepositoryGraph(repo);
        preStoredBranch = new SetOfCommits();
        commitsUnderLastCommon = new SetOfCommits();
        lastCommonCommits = new SetOfCommits();
    }
}
