package lastCommonCommitsGitHub.finder.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SetOfCommits {
    private HashSet<String> commitsSet;

    SetOfCommits() {
        commitsSet = new HashSet<>();
    }

    public boolean contains(String commit) {
        return commitsSet.contains(commit);
    }

    public boolean add(String commit) {
        return commitsSet.add(commit);
    }

    public boolean remove(String commit) {
        return commitsSet.remove(commit);
    }

    public List<String> getList() {
        return new ArrayList<>(commitsSet);
    }

    public void clear() {
        commitsSet.clear();
    }

    public int size() {
        return commitsSet.size();
    }

    @Override
    public String toString() {
        return commitsSet.toString();
    }
}
