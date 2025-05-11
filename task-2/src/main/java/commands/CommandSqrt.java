package commands;

import context.Context;

import java.util.List;

public class CommandSqrt implements ICommand {
    @Override
    public void execute(Context context, List<String> args) {
        if (!args.isEmpty()) {
            throw new IllegalArgumentException("Square root does not accept any arguments");
        }
        double var = context.popOperand();
        if (var < 0) {
            context.pushOperand(var);
            throw new IllegalArgumentException("Square root operation requires a non negative number");
        }
        context.pushOperand(Math.sqrt(var));
    }
}
