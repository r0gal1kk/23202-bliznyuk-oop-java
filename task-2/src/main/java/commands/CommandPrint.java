package commands;

import context.Context;

public class CommandPrint implements ICommand {
    @Override
    public void execute(Context context, String[] args) {
        if (args.length != 0) {
            throw new IllegalArgumentException("Print command does not accept any arguments");
        }
        System.out.println(context.topOperand());
    }
}
