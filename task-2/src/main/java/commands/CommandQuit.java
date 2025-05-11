package commands;

import context.Context;

import java.util.List;

public class CommandQuit implements ICommand {
    @Override
    public void execute(Context context, List<String> args) {
        log.info("Quit command executed");
        System.exit(0);
    }
}
