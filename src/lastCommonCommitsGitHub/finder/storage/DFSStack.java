package lastCommonCommitsGitHub.finder.storage;

import java.util.ArrayDeque;

public class DFSStack {
    private ArrayDeque<String> dfsCommits;

    DFSStack() {
        dfsCommits = new ArrayDeque<>();
    }

    public void push(String commit) {
        dfsCommits.push(commit);
    }

    public String pop() {
        return dfsCommits.pop();
    }

    public boolean isEmpty() {
        return dfsCommits.isEmpty();
    }
}
