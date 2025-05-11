package commands;

import context.Context;

import java.util.List;
import java.util.NoSuchElementException;

public class CommandPush implements ICommand {
    @Override
    public void execute(Context context, List<String> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Push command requires 1 argument");
        }
        Double var = context.getVariable(args.getFirst());
        if (var == null) {
            try {
                var = Double.parseDouble(args.getFirst());
            } catch (NumberFormatException e) {
                throw new NoSuchElementException(args.getFirst());
            }
        }
        context.pushOperand(var);
    }
}
