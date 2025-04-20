package commands;

import context.Context;

import java.util.NoSuchElementException;

public class CommandPush implements ICommand {
    @Override
    public void execute(Context context, String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Push command requires 1 argument");
        }
        Double var = context.getVariable(args[0]);
        if (var == null) {
            try {
                var = Double.parseDouble(args[0]);
            } catch (NumberFormatException e) {
                throw new NoSuchElementException(args[0]);
            }
        }
        context.pushOperand(var);
    }
}
