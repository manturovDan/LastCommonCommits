package lastCommonCommitsGitHub.finder;

import lastCommonCommitsGitHub.HTTPInteraction.HTTPGitHub;
import lastCommonCommitsGitHub.HTTPInteraction.HTTPGitHubMediator;
import lastCommonCommitsGitHub.finder.search.DeepFirstSearchInRepo;
import lastCommonCommitsGitHub.finder.storage.RepositoryGraph;

import java.io.IOException;
import java.util.Collection;

public class LastCommonCommitsFinderGitHub implements LastCommonCommitsFinder {
    private String owner;
    private String repo;
    private String token;
    private HTTPGitHub HTTPInteraction;

    public LastCommonCommitsFinderGitHub(String owner, String repo, String token) {
        this.owner = owner;
        this.repo = repo;
        this.token = token;
    }

    public void initialize() {
        HTTPInteraction = new HTTPGitHub(owner, repo, token);
        long lastEventId = HTTPInteraction.lastEvent();
        DeepFirstSearchInRepo search = new DeepFirstSearchInRepo(HTTPInteraction, lastEventId);
    }

    @Override
    public Collection<String> findLastCommonCommits(String branchA, String branchB) throws IOException {
        return null;
    }
}
