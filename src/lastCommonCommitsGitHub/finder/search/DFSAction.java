package lastCommonCommitsGitHub.finder.search;

import java.util.function.Function;

public interface DFSAction extends Function<String, DFSAction> { }
