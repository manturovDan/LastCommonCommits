package lastCommonCommitsGitHub.finder;

import lastCommonCommitsGitHub.HTTPInteraction.HTTPGitHub;
import lastCommonCommitsGitHub.finder.search.DeepFirstSearchInRepo;

import java.io.IOException;
import java.util.Collection;

public class LastCommonCommitsFinderGitHub implements LastCommonCommitsFinder {
    private String owner;
    private String repo;
    private String token;
    private HTTPGitHub HTTPInteraction;
    private DeepFirstSearchInRepo search;

    LastCommonCommitsFinderGitHub(String owner, String repo, String token) {
        this.owner = owner;
        this.repo = repo;
        this.token = token;
        HTTPInteraction = new HTTPGitHub(owner, repo, token);
    }

    @Override
    public Collection<String> findLastCommonCommits(String branchA, String branchB) {
        if (search == null)
            search = new DeepFirstSearchInRepo(HTTPInteraction);

        for (int attempt = 0; attempt < 5; ++attempt) {
            search.lastCommonCommits(branchA, branchB);

            if (search.getLastEventId() == HTTPInteraction.lastEvent())
                break;
        }

        return null;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
