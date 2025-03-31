package org.bullsAndCows;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Game {
    private final InputHandler inputHandler;
    private final NumberGenerator numberGenerator;
    private String secretNumber;
    private final GameUI gameUI;
    private static final Logger logger = LoggerFactory.getLogger(Game.class);

    public Game() {
        this.numberGenerator = new NumberGenerator();
        this.inputHandler = new InputHandler();
        gameUI = new GameUI();
        logger.debug("Game initialized.");
    }

    public Game(InputHandler inputHandler, NumberGenerator numberGenerator, GameUI gameUI) {
        this.inputHandler = inputHandler;
        this.numberGenerator = numberGenerator;
        this.gameUI = gameUI;
        logger.debug("Game initialized with dependencies.");
    }

    private Pair<Integer, Integer> countBullsAndCows(String guess) {
        logger.debug("Counting bulls and cows for guess: {}", guess);
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
        logger.debug("Result - Bulls: {}, Cows: {}", bulls, cows);
        return new Pair<>(bulls, cows);
    }

    public void run() {
        logger.info("Generating secret number...");
        secretNumber = numberGenerator.generateNumber().toString();
        logger.debug("Secret number: {}", secretNumber);

        while (true) {
            logger.info("Waiting for user input...");
            gameUI.printMessage("Введите свою догадку:");
            String guess = inputHandler.getInput();
            logger.debug("User input: {}", guess);

            if (guess.equals("q")) {
                logger.info("User quit the game...");
                gameUI.printMessage("Загаданное число: " + secretNumber + ". Игра окончена!");
                return;
            }
            if (!inputHandler.checkInput(guess)) {
                logger.warn("Invalid input received: {}", guess);
                gameUI.printMessage("Некорректный ввод. Введите четырёхзначное число.");
                continue;
            }
            if (secretNumber.equals(guess)) {
                logger.info("User guessed the secret number.");
                gameUI.printMessage("Вы угадали число!\n");
                return;
            }
            Pair<Integer, Integer> result = countBullsAndCows(guess);
            gameUI.printMessage("Быки: " + result.first + "\nКоровы: " + result.second);
        }
    }
}
