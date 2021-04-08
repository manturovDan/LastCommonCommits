package lastCommonCommitsGitHub.UI;

import java.awt.event.KeyEvent;
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

    private String currentBranchA;
    private String currentBranchB;

    public IOInteraction(InputStream is, PrintStream os) {
        inputStream = is;
        outputStream = os;
    }

    public IOInteraction() {
        this(System.in, System.out);
    }

    private String typeSmtInvitation(String field) {
        return "Type " + field + " (or '-' to go back): ";
    }

    private void printInvitation(String field) {
        outputStream.println(typeSmtInvitation(field));
    }

    private boolean mayContinueInput(String received) {
        return received.length() != 1 || received.charAt(0) != '-';
    }

    public boolean findOutGitHubRepositoryInfo(Scanner scanner) {
        outputStream.println("Repository menu");

        printInvitation("Owner");
        owner = scanner.nextLine();
        if (!mayContinueInput(owner))
            return false;

        printInvitation("REPOSITORY NAME");
        repo = scanner.nextLine();
        if (!mayContinueInput(repo))
            return false;

        printInvitation("TOKEN or empty line if unnecessary");
        token = scanner.nextLine();
        if (!mayContinueInput(token))
            return false;

        outputStream.println("Information was received");

        return true;
    }

    public boolean findOutBranchesName(Scanner scanner) {
        currentBranchA = null;
        currentBranchB = null;

        outputStream.println("Branches menu");

        printInvitation("branchA");
        currentBranchA = scanner.nextLine();
        if (!mayContinueInput(currentBranchA))
            return false;

        printInvitation("branchB");
        currentBranchB = scanner.nextLine();

        return mayContinueInput(currentBranchB);
    }

    public String getOwner() {
        if (owner == null)
            throw new NullPointerException("Owner was not retrieved");
        return owner;
    }

    public String getRepo() {
        if (repo == null)
            throw new NullPointerException("Repo was not retrieved");
        return repo;
    }

    public String getToken() {
        if (owner == null)
            throw new NullPointerException("Token was not retrieved");
        return token;
    }

    public String getCurrentBranchA() {
        if (currentBranchA == null)
            throw new NullPointerException("Branch A was not retrieved");
        return currentBranchA;
    }

    public String getCurrentBranchB() {
        if (currentBranchB == null)
            throw new NullPointerException("Branch A was not retrieved");
        return currentBranchB;
    }
}
