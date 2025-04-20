import context.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.Collections;

public class CalculatorTest {
    private Calculator calculator;
    private Context context;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() throws Exception {
        calculator = new Calculator();
        Field contextField = Calculator.class.getDeclaredField("context");
        contextField.setAccessible(true);
        context = (Context) contextField.get(calculator);
    }

    private String createTempFileWithCommands(String... commands) throws IOException {
        Path tempFile = tempDir.resolve("commands.txt");
        Files.write(tempFile, Collections.singletonList(String.join("\n", commands)));
        return tempFile.toString();
    }

    @Test
    void testCommandDefine() throws IOException {
        String filename = createTempFileWithCommands("DEFINE x 5");
        calculator.process(new String[]{filename});
        assertEquals(5.0, context.getVariable("x"), "Variable x should be defined as 5");
    }

    @Test
    void testCommandPushWithNumber() throws IOException {
        String filename = createTempFileWithCommands("PUSH 10");
        calculator.process(new String[]{filename});
        assertEquals(1, context.getSize(), "Stack size should be 1 after PUSH");
        assertEquals(10.0, context.topOperand(), "Top value should be 10");
    }

    @Test
    void testCommandPushWithVariable() throws IOException {
        String filename = createTempFileWithCommands("DEFINE x 7", "PUSH x");
        calculator.process(new String[]{filename});
        assertEquals(1, context.getSize(), "Stack size should be 1 after PUSH");
        assertEquals(7.0, context.topOperand(), "Top value should be 7 (value of x)");
    }

    @Test
    void testCommandPop() throws IOException {
        String filename = createTempFileWithCommands("PUSH 15", "POP");
        calculator.process(new String[]{filename});
        assertTrue(context.isEmpty(), "Stack should be empty after POP");
    }

    @Test
    void testCommandPrint() throws IOException {
        String filename = createTempFileWithCommands("PUSH 20", "PRINT");
        calculator.process(new String[]{filename});
        assertEquals(20.0, context.topOperand(), "PRINT should not modify the stack; top should still be 20");
    }

    @Test
    void testCommandSqrt() throws IOException {
        String filename = createTempFileWithCommands("PUSH 16", "SQRT");
        calculator.process(new String[]{filename});
        assertEquals(4.0, context.topOperand(), "Square root of 16 should be 4");
    }

    @Test
    void testCommandSub() throws IOException {
        String filename = createTempFileWithCommands("PUSH 4", "PUSH 10", "SUB");
        calculator.process(new String[]{filename});
        assertEquals(1, context.getSize(), "Stack size should be 1 after SUB");
        assertEquals(6.0, context.topOperand(), "10 - 4 should be 6");
    }

    @Test
    void testCommandAdd() throws IOException {
        String filename = createTempFileWithCommands("PUSH 3", "PUSH 5", "ADD");
        calculator.process(new String[]{filename});
        assertEquals(1, context.getSize(), "Stack size should be 1 after ADD");
        assertEquals(8.0, context.topOperand(), "3 + 5 should be 8");
    }

    @Test
    void testCommandDiv() throws IOException {
        String filename = createTempFileWithCommands("PUSH 4", "PUSH 20", "DIV");
        calculator.process(new String[]{filename});
        assertEquals(1, context.getSize(), "Stack size should be 1 after DIV");
        assertEquals(5.0, context.topOperand(), "20 / 4 should be 5");
    }

    @Test
    void testCommandMul() throws IOException {
        String filename = createTempFileWithCommands("PUSH 6", "PUSH 7", "MUL");
        calculator.process(new String[]{filename});
        assertEquals(1, context.getSize(), "Stack size should be 1 after MUL");
        assertEquals(42.0, context.topOperand(), "6 * 7 should be 42");
    }

    @Test
    void testUnknownCommand() throws IOException {
        String filename = createTempFileWithCommands("INVALID");
        calculator.process(new String[]{filename});
        assertEquals(0, context.getSize(), "Stack should remain empty for unknown command");
    }

    @Test
    void testPushInvalidNumber() throws IOException {
        String filename = createTempFileWithCommands("PUSH abc");
        calculator.process(new String[]{filename});
        assertEquals(0, context.getSize(), "Stack should remain empty after invalid number in PUSH");
    }

    @Test
    void testPushUndefinedVariable() throws IOException {
        String filename = createTempFileWithCommands("PUSH x");
        calculator.process(new String[]{filename});
        assertEquals(0, context.getSize(), "Stack should remain empty after pushing undefined variable");
    }

    @Test
    void testDefineInvalidValue() throws IOException {
        String filename = createTempFileWithCommands("DEFINE x abc");
        calculator.process(new String[]{filename});
        assertNull(context.getVariable("x"), "Variable x should not be defined with invalid value");
    }

    @Test
    void testAddInsufficientOperands() throws IOException {
        String filename = createTempFileWithCommands("PUSH 5", "ADD");
        calculator.process(new String[]{filename});
        assertEquals(1, context.getSize(), "Stack should remain unchanged after ADD with insufficient operands");
        assertEquals(5.0, context.topOperand(), "Top value should still be 5");
    }

    @Test
    void testDivByZero() throws IOException {
        String filename = createTempFileWithCommands("PUSH 0", "PUSH 10", "DIV");
        calculator.process(new String[]{filename});
        assertEquals(2, context.getSize(), "Stack should remain unchanged after division by zero");
        assertEquals(10, context.topOperand(), "Top value should still be 10");
    }

    @Test
    void testSqrtNegativeNumber() throws IOException {
        String filename = createTempFileWithCommands("PUSH -4", "SQRT");
        calculator.process(new String[]{filename});
        assertEquals(1, context.getSize(), "Stack should remain unchanged after SQRT on negative number");
        assertEquals(-4.0, context.topOperand(), "Top value should still be -4");
    }

    @Test
    void testPopEmptyStack() throws IOException {
        String filename = createTempFileWithCommands("POP");
        calculator.process(new String[]{filename});
        assertTrue(context.isEmpty(), "Stack should remain empty after POP on empty stack");
    }
}
