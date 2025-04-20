package commands;

import context.Context;

public class CommandSqrt implements ICommand {
    @Override
    public void execute(Context context, String[] args) {
        double var = context.popOperand();
        if (var < 0) {
            throw new IllegalArgumentException("Square root operation requires a non negative number");
        }
        context.pushOperand(Math.sqrt(var));
    }
}
