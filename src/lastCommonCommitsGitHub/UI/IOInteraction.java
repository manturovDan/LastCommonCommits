package lastCommonCommitsGitHub.UI;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IOInteraction {
    private final InputStream inputStream;
    private final PrintStream outputStream;
    private String owner;
    private String repo;
    private String token;

    public IOInteraction(InputStream is, PrintStream os) {
        inputStream = is;
        outputStream = os;
    }

    public IOInteraction() {
        this(System.in, System.out);
    }

    public void findOutGitHubRepositoryInfo() throws IOException {
        outputStream.println("Hello, it is program for finding last common commits");
        try (Scanner scanner = new Scanner(inputStream)) {
            outputStream.println("Input OWNER:");
            owner = scanner.nextLine();

            outputStream.println("Input REPOSITORY name: ");
            repo = scanner.nextLine();

            outputStream.println("Input TOKEN or empty line if it isn't necessary: ");
            token = scanner.nextLine();
        } catch (Exception e) {
            throw new IOException("input error");
        }
    }

    public String getOwner() {
        if (owner == null)
            throw new RuntimeException("Owner was not retrieved");
        return owner;
    }

    public String getRepo() {
        if (repo == null)
            throw new RuntimeException("Repo was not retrieved");
        return repo;
    }

    public String getToken() {
        if (owner == null)
            throw new RuntimeException("Token was not retrieved");
        return token;
    }
}
