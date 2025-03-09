import java.util.Random;

public class NumberGenerator {
    private int secretNumber;
    public int generateNumber() {
        Random rand = new Random();
        secretNumber = 1000 + rand.nextInt(9000);
        return secretNumber;
    }
    int getSecretNumber() {
        return secretNumber;
    }
}
