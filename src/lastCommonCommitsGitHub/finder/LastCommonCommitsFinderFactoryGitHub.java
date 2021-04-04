package lastCommonCommitsGitHub.finder;

public class LastCommonCommitsFinderFactoryGitHub implements LastCommonCommitsFinderFactory {
    @Override
    public LastCommonCommitsFinder create(String owner, String repo, String token) {
        LastCommonCommitsFinder finder = new LastCommonCommitsFinderGitHub(owner, repo, token);



        return finder;
    }
}
