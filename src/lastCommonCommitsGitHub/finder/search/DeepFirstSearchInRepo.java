package lastCommonCommitsGitHub.finder.search;

import lastCommonCommitsGitHub.HTTPInteraction.HTTPGitHub;
import lastCommonCommitsGitHub.finder.storage.SearchStorage;

public class DeepFirstSearchInRepo {
    private SearchStorage storage;
    private HTTPGitHub HTTPInteraction;

    public DeepFirstSearchInRepo(HTTPGitHub HTTPInteraction, long lastEventId) {
        this.HTTPInteraction = HTTPInteraction;
        storage = new SearchStorage(HTTPInteraction.getRepo(), lastEventId);
    }
}
