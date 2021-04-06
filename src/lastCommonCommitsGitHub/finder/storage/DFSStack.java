package lastCommonCommitsGitHub.finder.storage;

import java.util.ArrayDeque;

public class DFSStack {
    private ArrayDeque<String> dfsCommits;

    DFSStack() {
        dfsCommits = new ArrayDeque<>();
    }

    public void push(String commit) {
        dfsCommits.push(commit);
        //System.out.println(dfsCommits);
    }

    public String pop() {
        String res = dfsCommits.pop();
        //System.out.println(dfsCommits);
        return res;
    }

    public boolean isEmpty() {
        return dfsCommits.isEmpty();
    }
}
