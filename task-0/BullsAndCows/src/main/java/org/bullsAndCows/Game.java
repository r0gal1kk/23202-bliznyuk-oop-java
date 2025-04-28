package org.bullsAndCows;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Scanner;

public class Game {
    private final NumberGenerator numberGenerator;
    private final InputValidator inputValidator;
    private final Scanner scanner;
    private String secretNumber;
    private final Logger logger;

    public Game() {
        logger = LoggerFactory.getLogger(Game.class);
        this.numberGenerator = new NumberGenerator();
        this.inputValidator = new InputValidator();
        this.scanner = new Scanner(System.in);
        logger.info("Игра инициализирована.");
    }

    public Game(NumberGenerator numberGenerator, InputValidator inputValidator, Scanner scanner, Logger logger) {
        this.numberGenerator = numberGenerator;
        this.inputValidator = inputValidator;
        this.scanner = scanner;
        this.logger = logger;
        logger.info("Игра инициализирована.");
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
        logger.info("Генерация секретного числа...");
        secretNumber = numberGenerator.generateNumber().toString();

        while (true) {
            logger.info("Введите свою догадку:");
            String guess = scanner.nextLine();

            if (guess.equals("q")) {
                logger.info("Загаданное число: {}", secretNumber);
                return;
            }
            if (!inputValidator.checkInput(guess)) {
                logger.info("Некорректный ввод. Введите четырёхзначное число.");
                continue;
            }
            if (secretNumber.equals(guess)) {
                logger.info("Вы угадали число!\n");
                return;
            }
            Pair<Integer, Integer> result = countBullsAndCows(guess);
            logger.info("Быки: {} Коровы: {}", result.first, result.second);
        }
    }
}