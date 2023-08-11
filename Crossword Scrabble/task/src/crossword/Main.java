package crossword;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        System.out.print("Welcome to Crossword Scrabble\nBag: ");
        try (BufferedReader file = Files.newBufferedReader(Path.of(args[0]))) {
            file.lines()
                    .map(l -> l.split(" "))
                    .filter(l -> l.length == 2 && isNumber(l[1]))
                    .forEach(l -> System.out.print(l[0].repeat(Math.max(0, Integer.parseInt(l[1])))));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static boolean isNumber(String number) {
        return number.matches("\\d+");
    }
}