package commands;

import context.Context;

public class CommandDivision implements ICommand {
    @Override
    public void execute(Context context, String[] args) {
        double var1 = context.popOperand();
        double var2 = context.popOperand();
        if (var2 == 0) {
            throw new ArithmeticException("Division by zero");
        }
        context.pushOperand(var1 / var2);
    }
}
