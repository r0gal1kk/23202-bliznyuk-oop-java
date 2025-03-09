public class Game {
    private boolean gameActive;
    private final InputHandler inputHandler;
    private Player player;
    private final NumberGenerator numberGenerator;
    private String secretNumber;

    public Game(Player player) {
        this.player = player;
        this.gameActive = false;
        this.numberGenerator = new NumberGenerator();
        this.inputHandler = new InputHandler();
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
        gameActive = true;
        secretNumber = numberGenerator.generateNumber().toString();
        while (gameActive) {
            System.out.println("Enter your guess: ");
            String guess = inputHandler.getInput();
            if (!inputHandler.checkInput(guess)) {
                System.out.println("Input is incorrect. You need to enter four-digit number.\n");
                continue;
            }

        }
    }


}
