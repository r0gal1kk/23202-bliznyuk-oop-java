package commands;

import context.Context;

import java.util.List;

public class CommandPrint implements ICommand {
    @Override
    public void execute(Context context, List<String> args) {
        if (!args.isEmpty()) {
            throw new IllegalArgumentException("Print command does not accept any arguments");
        }
        System.out.println(context.topOperand());
    }
}
