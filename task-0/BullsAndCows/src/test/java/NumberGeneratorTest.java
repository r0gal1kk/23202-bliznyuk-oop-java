import org.bullsAndCows.*;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberGeneratorTest {
    private final NumberGenerator numberGenerator = new NumberGenerator();

    @RepeatedTest(100)
    void generatedNumberIsFourDigits() {
        int number = numberGenerator.generateNumber();
        assertTrue(number >= 1000 && number <= 9999,
                "Число должно быть четырехзначным: " + number);
    }

    @RepeatedTest(100)
    void firstDigitIsNotZero() {
        int number = numberGenerator.generateNumber();
        int firstDigit = number / 1000;
        assertNotEquals(0, firstDigit,
                "Первая цифра не должна быть 0: " + number);
    }

    @RepeatedTest(100)
    void allDigitsAreUnique() {
        int number = numberGenerator.generateNumber();
        String numberStr = String.valueOf(number);
        boolean hasUniqueDigits = numberStr.chars().distinct().count() == 4;
        assertTrue(hasUniqueDigits,
                "Все цифры должны быть уникальными: " + number);
    }

}