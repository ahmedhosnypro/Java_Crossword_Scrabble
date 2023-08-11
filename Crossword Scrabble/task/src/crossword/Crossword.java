package crossword;

import java.util.*;

public class Crossword {
    private final List<String> chars = new ArrayList<>();
    private final StringBuilder tray = new StringBuilder();

    void bag() {
        System.out.println();
        if (!tray.isEmpty()) {
            tray();
        }
        System.out.println("Bag: " + String.join("", chars) + "\n");
    }

    synchronized void shuffle() {
        if (chars.isEmpty()) {
            System.out.println("The bag is empty.");
        } else {
            Collections.shuffle(chars);
        }
        bag();
    }

    synchronized void add(String character, int count) {
        for (int i = 0; i < count; i++) {
            chars.add(character);
        }
    }


    synchronized void remove(String character) {
        chars.remove(character);
    }

    Random random = new Random();

    synchronized void pick() {
        if (chars.isEmpty()) {
            System.out.println("\nNo more tiles to pick.");
        } else {
            if (chars.size() < 7) {
                tray.append(chars.stream().map(String::valueOf).reduce("", (a, b) -> a + b));
                chars.clear();
            } else {
                for (int i = 0; i < 7; i++) {
                    int randomIndex = random.nextInt(chars.size());
                    String character = chars.get(randomIndex);
                    tray.append(character);
                    remove(character);
                }
            }
        }
        bag();
    }

    void tray() {
        System.out.println("Tray: " + tray + "\n");
    }
}
