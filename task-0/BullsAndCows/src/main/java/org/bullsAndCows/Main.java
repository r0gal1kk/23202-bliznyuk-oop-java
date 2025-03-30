package org.bullsAndCows;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Game started.");
        System.out.println("Добро пожаловать в игру Быки и Коровы.");
        Game game = new Game();
        game.run();
        logger.info("Game finished.");
    }
}
