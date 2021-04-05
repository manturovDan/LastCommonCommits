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

    LastCommonCommitsFinderGitHub(String owner, String repo, String token) {
        this.owner = owner;
        this.repo = repo;
        this.token = token;
        HTTPInteraction = new HTTPGitHub(owner, repo, token);
    }

    @Override
    public Collection<String> findLastCommonCommits(String branchA, String branchB) throws IOException {
        DeepFirstSearchInRepo search = new DeepFirstSearchInRepo(HTTPInteraction);

        for (int attempt = 0; attempt < 5; ++attempt) {
            long lastEventId = HTTPInteraction.lastEvent();
            search.buildGitGraph();

            if (lastEventId != HTTPInteraction.lastEvent())
                continue;

            //search.lastCommonCommits(branchA, branchB);

            if (lastEventId == HTTPInteraction.lastEvent())
                break;
        }

        return null;
    }
}
