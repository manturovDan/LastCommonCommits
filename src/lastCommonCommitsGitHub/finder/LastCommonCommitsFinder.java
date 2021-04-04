package lastCommonCommitsGitHub.finder;

import java.io.IOException;
import java.util.Collection;

public interface LastCommonCommitsFinder {
    Collection<String> findLastCommonCommits(String branchA, String branchB) throws IOException;
}
