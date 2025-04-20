package commands;

import context.Context;

import java.util.EmptyStackException;

public class CommandMultiplication implements ICommand {
    @Override
    public void execute(Context context, String[] args) {
        if (args.length != 0) {
            throw new IllegalArgumentException("Multiplication does not accept any arguments");
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
        double result = var1 * var2;
        if (result > Double.MAX_VALUE || result < -Double.MAX_VALUE) {
            context.pushOperand(var2);
            context.pushOperand(var1);
            throw new ArithmeticException("Multiplication is out of range");
        }
        context.pushOperand(result);
    }
}
