package commands;

import context.Context;

import java.util.List;

public class CommandPop implements ICommand {
    @Override
    public void execute(Context context, List<String> args) {
        if (!args.isEmpty()) {
            throw new IllegalArgumentException("Pop command does not accept any arguments");
        }
        context.popOperand();
    }
}
