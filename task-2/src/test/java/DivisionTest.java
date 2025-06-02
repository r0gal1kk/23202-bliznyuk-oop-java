import commands.CommandDivision;
import commands.ICommand;
import context.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class DivisionTest {
    private ICommand command;
    private Context context;

    @BeforeEach
    void setUp() {
        command = new CommandDivision();
        context = new Context();
    }

    @Test
    void testDivValidNumbers() {
        context.pushOperand(4.0);
        context.pushOperand(20.0);
        List<String> args = Arrays.asList();
        command.execute(context, args);
        assertEquals(1, context.getSize(), "Stack size should be 1 after DIV");
        assertEquals(5.0, context.topOperand(), "20 / 4 should be 5");
    }

    @Test
    void testDivByZero() {
        context.pushOperand(0.0);
        context.pushOperand(10.0);
        List<String> args = Arrays.asList();
        assertThrows(ArithmeticException.class, () -> command.execute(context, args),
                "Should throw ArithmeticException for division by zero");
        assertEquals(2, context.getSize(), "Stack should remain unchanged");
        assertEquals(10.0, context.topOperand(), "Top value should still be 10");
    }
}