package lastCommonCommitsGitHub.finder;

import java.util.HashMap;

public class LastCommonCommitsFinderFactoryGitHub implements LastCommonCommitsFinderFactory {
    private final HashMap<String, LastCommonCommitsFinder> findersHash = new HashMap<>();

    @Override
    public LastCommonCommitsFinder create(String owner, String repo, String token) {
        LastCommonCommitsFinder finder;

        String repoKey = getKeyByOwnerRepo(owner, repo);
        if (findersHash.containsKey(repoKey)) {
            finder = findersHash.get(repoKey);
            ((LastCommonCommitsFinderGitHub)finder).setToken(token);
        }
        else {
            finder = new LastCommonCommitsFinderGitHub(owner, repo, token);
            findersHash.put(repoKey, finder);
        }

        return finder;
    }

    private String getKeyByOwnerRepo(String owner, String repo) {
        return owner + "/" + repo;
    }
}
