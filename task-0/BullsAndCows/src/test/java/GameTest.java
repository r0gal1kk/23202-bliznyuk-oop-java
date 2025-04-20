import org.bullsAndCows.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.Scanner;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameTest {
    @Mock
    private NumberGenerator numberGenerator;
    @Mock
    private InputValidator inputValidator;
    @Mock
    private Scanner scanner;
    @Mock
    private Logger logger;

    @InjectMocks
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game(numberGenerator, inputValidator, scanner, logger);
    }

    @Test
    void correctGuessEndsGame() {
        when(numberGenerator.generateNumber()).thenReturn(1234);
        when(scanner.nextLine()).thenReturn("1234");
        when(inputValidator.checkInput("1234")).thenReturn(true);

        game.run();

        verify(logger).info("Вы угадали число!\n");
    }

    @Test
    void showsBullsAndCows() {
        when(numberGenerator.generateNumber()).thenReturn(1234);
        when(scanner.nextLine())
                .thenReturn("1243")
                .thenReturn("q");
        when(inputValidator.checkInput(anyString())).thenReturn(true);

        game.run();

        verify(logger).info("Быки: {} Коровы: {}", 2, 2);
    }
}