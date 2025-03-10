import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NumberGenerator {
    public Integer generateNumber() {
        List<Integer> digits = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            digits.add(i);
        }
        Collections.shuffle(digits);
        int firstDigit = digits.getFirst();
        if (firstDigit == 0) {
            firstDigit = digits.get(1);
            digits.remove(1);
        } else {
            digits.removeFirst();
        }
        int number = firstDigit;
        for (int i = 0; i < 3; i++) {
            number = number * 10 + digits.get(i);
        }
        return number;
    }
}
