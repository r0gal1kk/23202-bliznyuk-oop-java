package commands;

import context.Context;

public class CommandPlus implements ICommand {
    @Override
    public void execute(Context context, String[] args) {
        double var1 = context.popOperand();
        double var2 = context.popOperand();
        double result = var1 + var2;
        if (result > Double.MAX_VALUE || result < -Double.MAX_VALUE) {
            throw new ArithmeticException("Subtraction is out of range");
        }
        context.pushOperand(result);
    }
}
