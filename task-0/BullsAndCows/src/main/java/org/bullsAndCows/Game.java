public class Game {
    private final InputHandler inputHandler;
    private final NumberGenerator numberGenerator;
    private String secretNumber;
    private int maxAttempts;

    public Game(int maxAttempts) {
        this.numberGenerator = new NumberGenerator();
        this.inputHandler = new InputHandler();
        this.maxAttempts = maxAttempts;
    }

    private Pair<Integer, Integer> countBullsAndCows(String guess) {
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == secretNumber.charAt(i)) {
                bulls++;
            }
            else if (secretNumber.contains(String.valueOf(guess.charAt(i)))) {
                cows++;
            }
        }
        return new Pair<>(bulls, cows);
    }

    public void run() {
        System.out.println("Game has been started. Try to guess the secret number.");
        secretNumber = numberGenerator.generateNumber().toString();
        int attempts = 0;
        while (attempts < maxAttempts) {
            System.out.println("Enter your guess: ");
            String guess = inputHandler.getInput();
            if (!inputHandler.checkInput(guess)) {
                System.out.println("Input is incorrect. You need to enter four-digit number.");
                continue;
            }
            if (secretNumber.equals(guess)) {
                System.out.println("You win!\n");
                return;
            }
            Pair<Integer, Integer> result = countBullsAndCows(guess);
            attempts++;
            System.out.println("Bulls: " + result.first + "\nCows: " + result.second);
            System.out.println("Attempts left: " + (maxAttempts - attempts));
        }
        System.out.println("You lost!\nThe secret number is: " + secretNumber);
    }
}
