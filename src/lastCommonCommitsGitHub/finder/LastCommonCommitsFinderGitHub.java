package lastCommonCommitsGitHub.finder;

import lastCommonCommitsGitHub.HTTPInteraction.HTTPGitHub;
import lastCommonCommitsGitHub.finder.search.DepthFirstSearchInRepo;

import java.io.IOException;
import java.util.Collection;

public class LastCommonCommitsFinderGitHub implements LastCommonCommitsFinder {
    private final String owner;
    private final String repo;
    private final HTTPGitHub HTTPInteraction;
    private DepthFirstSearchInRepo search;
    private static final int attemptsCount = 5;

    public LastCommonCommitsFinderGitHub(String owner, String repo, String token) {
        this.owner = owner;
        this.repo = repo;
        HTTPInteraction = new HTTPGitHub(owner, repo, token);
    }

    @Override
    public Collection<String> findLastCommonCommits(String branchA, String branchB) throws IOException {
        if (search == null)
            search = new DepthFirstSearchInRepo(HTTPInteraction);

        Collection<String> result;

        //pushes can affect on our functionality (for example they can contain rebase)
        //tested via breakpoint
        for (int attempt = 0; attempt < attemptsCount; ++attempt) {
            result = search.lastCommonCommits(branchA, branchB);

            if (search.getLastEventId() == HTTPInteraction.lastEvent())
                return result;
        }

        throw new IOException("Failed to get information from repository for " + attemptsCount + " times due to remote update");
    }

    public void setToken(String token) {
        HTTPInteraction.setToken(token);
    }
}
