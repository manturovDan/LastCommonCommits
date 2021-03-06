package lastCommonCommitsGitHub.finder.storage;

import java.util.ArrayDeque;

public class DFSStack {
    private final ArrayDeque<String> dfsCommits;

    DFSStack() {
        dfsCommits = new ArrayDeque<>();
    }

    public void push(String commit) {
        dfsCommits.push(commit);
    }

    public String pop() {
        return dfsCommits.pop();
    }

    public String getTop() {
        return dfsCommits.getFirst();
    }

    public boolean isEmpty() {
        return dfsCommits.isEmpty();
    }
}
