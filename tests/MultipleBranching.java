import lastCommonCommitsGitHub.finder.LastCommonCommitsFinder;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinderFactory;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinderFactoryGitHub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class MultipleBranching {
    @Test
    public void complexRepo() throws IOException {
        System.out.println("MultipleBranching Test");
        LastCommonCommitsFinderFactory factory = new LastCommonCommitsFinderFactoryGitHub();
        LastCommonCommitsFinder finder = factory.create("manturovDanExperimental", "multipleBranching", OAuthToken.token);
        int i = 1;
        for (String branchesPair : MultipleBranchingMergeBase.lastCommon.keySet()) {
            String[] branches = branchesPair.split(":");
            Collection<String> result;
            System.out.print(i + "/" + MultipleBranchingMergeBase.lastCommon.size() + " ");
            if (i % 2 == 1) {
                result = finder.findLastCommonCommits(branches[0], branches[1]);
                 System.out.println(Arrays.toString(branches));
            }
            else {
                System.out.println(Arrays.toString(new String[] { branches[1], branches[0] }));
                result = finder.findLastCommonCommits(branches[1], branches[0]);
            }

            Assertions.assertEquals(new HashSet<>(MultipleBranchingMergeBase.lastCommon.get(branchesPair)), new HashSet<>(result));
            i++;
        }
    }
}
