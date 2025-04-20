package commands;

import context.Context;

public class CommandPop implements ICommand {
    @Override
    public void execute(Context context, String[] args) {
        if (args.length != 0) {
            throw new IllegalArgumentException("Pop command does not accept any arguments");
        }
        context.popOperand();
    }
}
