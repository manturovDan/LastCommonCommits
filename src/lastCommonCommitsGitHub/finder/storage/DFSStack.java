package lastCommonCommitsGitHub.finder.storage;

import java.util.ArrayDeque;

public class DFSStack {
    private ArrayDeque<String> dfsCommits;

    DFSStack() {
        dfsCommits = new ArrayDeque<>();
    }

    void push(String commit) {
        dfsCommits.push(commit);
    }

    String pop() {
        return dfsCommits.pop();
    }

    boolean isEmpty() {
        return dfsCommits.isEmpty();
    }
}
