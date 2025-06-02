package commands;

import context.Context;

import java.util.EmptyStackException;
import java.util.List;

public class CommandDivision implements ICommand {
    @Override
    public void execute(Context context, List<String> args) {
        if (!args.isEmpty()) {
            throw new IllegalArgumentException("Division does not accept any arguments");
        }
        Double var1 = null;
        Double var2 = null;
        try {
            var1 = context.popOperand();
            var2 = context.popOperand();
        } catch (EmptyStackException e) {
            if (var1 != null) {
                context.pushOperand(var1);
            }
            throw new IllegalArgumentException("There is not enough operands in the stack");
        }
        if (var2 == 0) {
            context.pushOperand(var2);
            context.pushOperand(var1);
            throw new ArithmeticException("Division by zero");
        }
        context.pushOperand(var1 / var2);
    }
}
