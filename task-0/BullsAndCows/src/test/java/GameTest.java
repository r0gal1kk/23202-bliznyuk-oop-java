import org.bullsAndCows.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameTest {
    @Mock
    private InputHandler inputHandler;
    @Mock
    private NumberGenerator numberGenerator;
    @Mock
    private GameUI gameUI;

    @InjectMocks
    private Game game;

    @Test
    void correctGuessEndsGame() {
        when(numberGenerator.generateNumber()).thenReturn(1234);
        when(inputHandler.getInput()).thenReturn("1234");
        when(inputHandler.checkInput("1234")).thenReturn(true);

        game.run();

        verify(gameUI).printMessage("Вы угадали число!\n");
    }

    @Test
    void showsBullsAndCows() {
        when(numberGenerator.generateNumber()).thenReturn(1234);
        when(inputHandler.getInput())
                .thenReturn("1243")
                .thenReturn("q");
        when(inputHandler.checkInput(anyString())).thenReturn(true);

        game.run();

        verify(gameUI).printMessage("Быки: 2\nКоровы: 2");
    }
}