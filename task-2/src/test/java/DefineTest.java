import commands.CommandDefine;
import commands.ICommand;
import context.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DefineTest {
    private ICommand command;
    private Context context;

    @BeforeEach
    void setUp() {
        command = new CommandDefine();
        context = new Context();
    }

    @Test
    void testDefineValidVariable() {
        String[] args = {"x", "5"};
        command.execute(context, args);
        assertEquals(5.0, context.getVariable("x"));
    }

    @Test
    void testDefineInvalidArgsTooFew() {
        String[] args = {"x"};
        assertThrows(IllegalArgumentException.class, () -> command.execute(context, args));
        assertNull(context.getVariable("x"));
    }

    @Test
    void testDefineInvalidArgsExtra() {
        String[] args = {"x", "5", "extra"};
        assertThrows(IllegalArgumentException.class, () -> command.execute(context, args));
        assertNull(context.getVariable("x"));
    }

    @Test
    void testDefineInvalidVariableName() {
        String[] args = {"1invalid", "5"};
        assertThrows(IllegalArgumentException.class, () -> command.execute(context, args));
        assertNull(context.getVariable("1invalid"));
    }

    @Test
    void testDefineInvalidNumberFormat() {
        String[] args = {"x", "abc"};
        assertThrows(IllegalArgumentException.class, () -> command.execute(context, args));
        assertNull(context.getVariable("x"));
    }
}