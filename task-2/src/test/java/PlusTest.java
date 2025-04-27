import commands.CommandPlus;
import commands.ICommand;
import context.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlusTest {
    private ICommand command;
    private Context context;

    @BeforeEach
    void setUp() {
        command = new CommandPlus();
        context = new Context();
    }

    @Test
    void testAddValidNumbers() {
        context.pushOperand(3.0);
        context.pushOperand(5.0);
        String[] args = {};
        command.execute(context, args);
        assertEquals(1, context.getSize());
        assertEquals(8.0, context.topOperand());
    }

    @Test
    void testAddWithArguments() {
        context.pushOperand(3.0);
        context.pushOperand(5.0);
        String[] args = {"extra"};
        assertThrows(IllegalArgumentException.class, () -> command.execute(context, args));
        assertEquals(2, context.getSize());
        assertEquals(5.0, context.topOperand());
    }

    @Test
    void testEmptyStack() {
        String[] args = {};
        assertThrows(IllegalArgumentException.class, () -> command.execute(context, args));
        assertTrue(context.isEmpty());
    }

    @Test
    void testOneNumber() {
        context.pushOperand(5.0);
        String[] args = {};
        assertThrows(IllegalArgumentException.class, () -> command.execute(context, args));
        assertEquals(1, context.getSize());
        assertEquals(5.0, context.topOperand());
    }

    @Test
    void testAddOverflow() {
        context.pushOperand(Double.MAX_VALUE);
        context.pushOperand(Double.MAX_VALUE);
        String[] args = {};
        assertThrows(ArithmeticException.class, () -> command.execute(context, args));
        assertEquals(2, context.getSize());
        assertEquals(Double.MAX_VALUE, context.topOperand());
    }
}