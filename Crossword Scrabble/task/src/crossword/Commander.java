package crossword;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Commander {
    private final Crossword crossword = new Crossword();
    private static final String COMMANDS = """
            What would you like to do?
                        
            A. Shuffle
                        
            B. Pick
                        
            C. Exit
                        
            """;

    public void start(String crosswordFile) {
        System.out.println("Welcome to Crossword Scrabble\n");
        setup(crosswordFile);
        crossword.bag();
        System.out.println(COMMANDS);
        Scanner scanner = new Scanner(System.in);
        while (execute(scanner.nextLine())) {
            System.out.println(COMMANDS);
        }
    }

    public boolean execute(String command) {
        switch (Command.from(command)) {
            case SHUFFLE -> crossword.shuffle();
            case PICK -> crossword.pick();
            case EXIT -> {
                System.out.println("\nBye.");
                return false;
            }
            case UNKNOWN -> {
                System.out.println("\nInvalid Command.");
                return true;
            }
        }
        return true;
    }


    private void setup(String crosswordFile) {
        try (BufferedReader file = Files.newBufferedReader(Path.of(crosswordFile))) {
            file.lines()
                    .map(l -> l.split(" "))
                    .filter(l -> l.length == 2 && isNumber(l[1]))
                    .forEach(l -> crossword.add(l[0], Integer.parseInt(l[1])));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static boolean isNumber(String number) {
        return number.matches("\\d+");
    }
}
