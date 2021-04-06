package lastCommonCommitsGitHub.finder.search;

import lastCommonCommitsGitHub.HTTPInteraction.HTTPGitHub;
import lastCommonCommitsGitHub.HTTPInteraction.JSONHandler;
import lastCommonCommitsGitHub.finder.storage.SearchStorage;

import java.util.AbstractMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class DeepFirstSearchInRepo {
    private final SearchStorage storage;
    private HTTPGitHub HTTPInteraction;

    public DeepFirstSearchInRepo(HTTPGitHub HTTPInteraction) {
        this.HTTPInteraction = HTTPInteraction;
        storage = new SearchStorage(HTTPInteraction.getRepo());
    }

    public String buildGitGraph(String branchName) {
        JSONHandler.JSONCommitParser commits = HTTPInteraction.getCommits(branchName);
        String topCommit = null;
        while (commits.hasNext()) {
            AbstractMap.SimpleEntry<String, List<String>> commit = commits.next();
            storage.addCommitInRepo(commit);
            if (topCommit == null)
                topCommit = commit.getKey();
        }

        return topCommit;
    }

    public void lastCommonCommits(String branchA, String branchB) {
        String topBranchA = buildGitGraph(branchA);
        storage.copyCommitsFromGraphToPreStoredBranch();
        String topBranchB = buildGitGraph(branchB);

        storage.getDfsStack().push(topBranchB);

        System.out.println(storage.getPreStoredBranch());
        searchDeeper(this::handleCommitAsPreStored);

        System.out.println(storage.getLastCommonCommits());
    }

    private void searchDeeper(Function<String, Integer> action) {
        while (!storage.getDfsStack().isEmpty()) {
            String currentCommit = storage.getDfsStack().pop();
            int nextAct = action.apply(currentCommit);//make action here
            if (nextAct == 0) {
                action = this::handleCommitAsPreStored;
            }
            else {
                action = this::handleCommitAsCommon;
            }
        }
    }

    private Integer handleCommitAsPreStored(String commit) {
        if (storage.getPreStoredBranch().contains(commit)) {
            if(!storage.getCommitsUnderLastCommon().contains(commit)) {
                storage.getLastCommonCommits().add(commit);
                //System.out.println(storage.getLastCommonCommits());
                pushCommitsListInStack(storage.getRepositoryGraph().getParents(commit));
                return 1;
            }
        }
        else {
            pushCommitsListInStack(storage.getRepositoryGraph().getParents(commit));
        }

        return 0;
    }

    private Integer handleCommitAsCommon(String commit) {
        if (!storage.getCommitsUnderLastCommon().contains(commit)) {
            storage.getCommitsUnderLastCommon().add(commit);
            storage.getLastCommonCommits().remove(commit);

            List<String> parents = storage.getRepositoryGraph().getParents(commit);
            if (parents.size() > 0) {
                pushCommitsListInStack(parents); //возврат в первую функцию, если нет родителей
                return 1;
            }
            else {
                return 0;
            }
        }

        return 0;
        //возврат в первую функцию
    }

    private void pushCommitsListInStack(List<String> commitsList) {
        for (String commit : commitsList) {
            storage.getDfsStack().push(commit);
        }
    }
}
