package org.bullsAndCows;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Scanner;

public class InputHandler {
    private static final Logger logger = LoggerFactory.getLogger(InputHandler.class);
    private final Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
        logger.debug("InputHandler initialized");
    }

    public String getInput() {
        String input = scanner.nextLine();
        logger.debug("Input received: {}", input);
        return input;
    }

    public boolean checkInput(String input) {
        boolean isValid = input.matches("\\d{4}");
        logger.debug("Input validation result for '{}': {}", input, isValid);
        return isValid;
    }
}