package org.bullsAndCows;

public class InputValidator {
    public boolean checkInput(String input) {
        return input.matches("\\d{4}");
    }
}
