package commands;

import context.Context;

public class CommandPrint implements ICommand {
    @Override
    public void execute(Context context, String[] args) {
        System.out.println(context.topOperand());
    }
}
