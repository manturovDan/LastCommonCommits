import lastCommonCommitsGitHub.finder.LastCommonCommitsFinder;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinderFactory;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinderFactoryGitHub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class MultipleBranching {
    @Test
    public void complexRepo() throws IOException {
        System.out.println("MultipleBranching Test");
        LastCommonCommitsFinderFactory factory = new LastCommonCommitsFinderFactoryGitHub();
        LastCommonCommitsFinder finder = factory.create("manturovDanExperimental", "multipleBranching", AlgorithmTests.token);
        for (String branchesPair : MultipleBranchingMergeBase.lastCommon.keySet()) {
            String[] branches = branchesPair.split(":");
            System.out.println(Arrays.toString(branches));
            Collection<String> result = finder.findLastCommonCommits(branches[0], branches[1]);

            Assertions.assertEquals(new HashSet<>(MultipleBranchingMergeBase.lastCommon.get(branchesPair)), new HashSet<>(result));

        }
    }
}
