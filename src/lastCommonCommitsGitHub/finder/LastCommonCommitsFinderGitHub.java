package lastCommonCommitsGitHub.finder;

import lastCommonCommitsGitHub.HTTPInteraction.HTTPGitHub;
import lastCommonCommitsGitHub.finder.search.DepthFirstSearchInRepo;

import java.io.IOException;
import java.util.Collection;

public class LastCommonCommitsFinderGitHub implements LastCommonCommitsFinder {
    private final String owner;
    private final String repo;
    private String token;
    private final HTTPGitHub HTTPInteraction;
    private DepthFirstSearchInRepo search;
    private static final int attemptsCount = 5;

    LastCommonCommitsFinderGitHub(String owner, String repo, String token) {
        this.owner = owner;
        this.repo = repo;
        this.token = token;
        HTTPInteraction = new HTTPGitHub(owner, repo, token);
    }

    @Override
    public Collection<String> findLastCommonCommits(String branchA, String branchB) throws IOException {
        if (search == null)
            search = new DepthFirstSearchInRepo(HTTPInteraction);

        Collection<String> result;

        for (int attempt = 0; attempt < attemptsCount; ++attempt) {
            result = search.lastCommonCommits(branchA, branchB);

            if (search.getLastEventId() == HTTPInteraction.lastEvent())
                return result;
        }

        throw new IOException("Failed to get information from repository for " + attemptsCount + " times due to remote update");
    }

    public void setToken(String token) {
        this.token = token;
    }
}
