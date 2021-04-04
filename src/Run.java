import UI.IOInteraction;

import java.io.IOException;

public class Run {
    public static void main(String[] args) throws IOException {
        IOInteraction ui = new IOInteraction();
        ui.findOutGithubRepositoryInfo();
    }
}
