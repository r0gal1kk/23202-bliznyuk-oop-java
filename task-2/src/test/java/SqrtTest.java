import commands.CommandSqrt;
import commands.ICommand;
import context.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class SqrtTest {
    private ICommand command;
    private Context context;

    @BeforeEach
    void setUp() {
        command = new CommandSqrt();
        context = new Context();
    }

    @Test
    void testSqrtValidNumber() {
        context.pushOperand(16.0);
        List<String> args = Arrays.asList();
        command.execute(context, args);
        assertEquals(1, context.getSize());
        assertEquals(4.0, context.topOperand());
    }

    @Test
    void testSqrtNegativeNumber() {
        context.pushOperand(-4.0);
        List<String> args = Arrays.asList();
        assertThrows(IllegalArgumentException.class, () -> command.execute(context, args));
        assertEquals(1, context.getSize());
        assertEquals(-4.0, context.topOperand());
    }
}