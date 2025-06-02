package context;

import java.util.EmptyStackException;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;

public class Context {
    private final Map<String, Double> var = new HashMap<>();
    private final Stack<Double> operands = new Stack<>();

    public int getSize() {
        return operands.size();
    }

    public boolean isEmpty() {
        return operands.isEmpty();
    }

    public Double popOperand() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return operands.pop();
    }

    public double topOperand() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return operands.peek();
    }

    public void pushOperand(Double operand) {
        operands.push(operand);
    }

    public void defineVariable(String name, Double value) {
        var.put(name, value);
    }

    public Double getVariable(String name) {
        return var.get(name);
    }
}
