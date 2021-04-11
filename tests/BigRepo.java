import lastCommonCommitsGitHub.finder.LastCommonCommitsFinder;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinderFactory;
import lastCommonCommitsGitHub.finder.LastCommonCommitsFinderFactoryGitHub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collection;

public class BigRepo {
    @Test
    public void Sake_Master_LangCreating() throws IOException {
        LastCommonCommitsFinderFactory factory = new LastCommonCommitsFinderFactoryGitHub();
        LastCommonCommitsFinder finder = factory.create("manturovDan", "Sake", OAuthToken.tokenManturovDan);

        Collection<String> result = finder.findLastCommonCommits("master", "langCreating");

        Assertions.assertEquals(result.size(), 1);
        Assertions.assertTrue(result.contains("5e4829514ca1b621ed9f667b28a6986c265ca78c"));
    }

    @Test
    public void Sake_LangCreating_Master() throws IOException {
        LastCommonCommitsFinderFactory factory = new LastCommonCommitsFinderFactoryGitHub();
        LastCommonCommitsFinder finder = factory.create("manturovDan", "Sake", OAuthToken.tokenManturovDan);

        Collection<String> result = finder.findLastCommonCommits("langCreating", "master");

        Assertions.assertEquals(result.size(), 1);
        Assertions.assertTrue(result.contains("5e4829514ca1b621ed9f667b28a6986c265ca78c"));

        LastCommonCommitsFinder finder2 = factory.create("manturovDan", "Sake", OAuthToken.tokenManturovDan);
        Assertions.assertEquals(finder, finder2);

        Collection<String> result2 = finder2.findLastCommonCommits("langCreating", "parsing");

        Assertions.assertEquals(result2.size(), 1);
        Assertions.assertTrue(result2.contains("5e4829514ca1b621ed9f667b28a6986c265ca78c"));
    }

    @Test
    public void Sake_lastTests_wayOut() throws IOException {
        LastCommonCommitsFinderFactory factory = new LastCommonCommitsFinderFactoryGitHub();
        LastCommonCommitsFinder finder = factory.create("manturovDan", "Sake", OAuthToken.tokenManturovDan);

        Collection<String> result = finder.findLastCommonCommits("lastTests", "wayOut");

        Assertions.assertEquals(result.size(), 1);
        Assertions.assertTrue(result.contains("0ad9f0a5576d5e017714e4e9483cc03b8d63a123"));
    }
}
