package lastCommonCommitsGitHub.finder.storage;

import java.util.HashSet;

public class SetOfCommits {
    protected HashSet<String> commitsSet;

    void establishSet(HashSet<String> commitsSet) {
        this.commitsSet = commitsSet;
    }
}
