package lastCommonCommitsGitHub.finder;

import lastCommonCommitsGitHub.HTTPInteraction.HTTPGitHub;
import lastCommonCommitsGitHub.HTTPInteraction.HTTPGitHubMediator;
import lastCommonCommitsGitHub.finder.repo.RepositoryGraph;

import java.io.IOException;
import java.util.Collection;

public class LastCommonCommitsFinderGitHub implements LastCommonCommitsFinder {
    private String owner;
    private String repo;
    private String token;
    private HTTPGitHubMediator HTTPInteraction;

    public LastCommonCommitsFinderGitHub(String owner, String repo, String token) {
        this.owner = owner;
        this.repo = repo;
        this.token = token;
    }

    public void initialize() {
        RepositoryGraph commits = new RepositoryGraph();
        HTTPInteraction = new HTTPGitHub(owner, repo, token);
    }

    @Override
    public Collection<String> findLastCommonCommits(String branchA, String branchB) throws IOException {
        return null;
    }
}
