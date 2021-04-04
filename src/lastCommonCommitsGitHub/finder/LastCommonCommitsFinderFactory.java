package lastCommonCommitsGitHub.finder;

public interface LastCommonCommitsFinderFactory {
    LastCommonCommitsFinder create(String owner, String repo, String token);
}
