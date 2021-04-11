import lastCommonCommitsGitHub.finder.LastCommonCommitsFinder;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinderFactory;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinderFactoryGitHub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class Rhombus {
    @Test
    public void rhombusTest() throws IOException {
        System.out.println("Rhombus Test");
        LastCommonCommitsFinderFactory factory = new LastCommonCommitsFinderFactoryGitHub();
        LastCommonCommitsFinder finder = factory.create("manturovDanExperimental", "rhombus", OAuthToken.tokenManturovDanExperimental);
        int i = 1;
        for (String branchesPair : RhombusMergeBase.lastCommon.keySet()) {
            String[] branches = branchesPair.split(":");
            Collection<String> result;
            System.out.print(i + "/" + RhombusMergeBase.lastCommon.size() + " ");

            result = finder.findLastCommonCommits(branches[0], branches[1]);
            System.out.print(Arrays.toString(branches) + " ");
            Assertions.assertEquals(new HashSet<>(RhombusMergeBase.lastCommon.get(branchesPair)), new HashSet<>(result));

            System.out.println(Arrays.toString(new String[] { branches[1], branches[0] }));
            result = finder.findLastCommonCommits(branches[1], branches[0]);
            Assertions.assertEquals(new HashSet<>(RhombusMergeBase.lastCommon.get(branchesPair)), new HashSet<>(result));

            i++;
        }
    }
}
