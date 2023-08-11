package crossword;

import java.util.Arrays;

public enum Command {
    SHUFFLE("A"),
    PICK("B"),
    EXIT("C"),
    UNKNOWN("");

    private final String option;

    Command(String option) {
        this.option = option;
    }

    public static Command from(String command) {
        try {
            return Arrays.stream(Command.values()).filter(c -> c.option.equalsIgnoreCase(command)).findFirst().orElseGet(() ->
                    Command.valueOf(command.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
