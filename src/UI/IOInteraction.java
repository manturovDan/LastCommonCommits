package UI;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IOInteraction {
    private final InputStream inputStream;
    private final PrintStream outputStream;

    public IOInteraction(InputStream is, PrintStream os) {
        inputStream = is;
        outputStream = os;
    }

    public IOInteraction() {
        this(System.in, System.out);
    }

    public void findOutGithubRepositoryInfo() {
        outputStream.println("Hello, it is program for finding last common commits");
        try (Scanner scanner = new Scanner(inputStream)) {
            outputStream.println("Input OWNER:");
            String owner = scanner.nextLine();

            outputStream.println("Input REPOSITORY name: ");
            String repo = scanner.nextLine();

            outputStream.println("Input TOKEN or empty line if it isn't necessary: ");
            String token = scanner.nextLine();

            outputStream.println(owner + " " + repo + " " + token);
        }
    }
}
