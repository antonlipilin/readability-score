package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String fileName = args[0];
        try {
            TextEntity textEntity = new TextEntity(readFileAsString(fileName));
            textEntity.printInfoAboutText();
            String typeOfScore = readScoreFromConsole();
            printScore(textEntity, typeOfScore);
        } catch (IOException e) {
            System.out.println("Unknown error");
        }
    }

    private static String readScoreFromConsole() {
        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        return new Scanner(System.in).nextLine();
    }

    private static void printScore(TextEntity text, String typeOfScore) {
        System.out.println();
        switch (typeOfScore) {
            case "ARI" -> {
                text.printAutomatedReadabilityIndex();
            }
            case "FK" ->  {
                text.printFleschKincaidIndex();
            }
            case "SMOG" -> {
                text.printSMOGIndex();
            }
            case "CL" -> {
                text.printColemanLiauIndex();
            }
            case "all" -> {
                text.printAllScores();
            }
            default -> System.out.println("Unknown type of score");
        }
    }
    private static String readFileAsString(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)));
    }

}
