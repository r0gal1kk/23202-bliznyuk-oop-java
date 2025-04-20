package commands;

import context.Context;

public class CommandQuit implements ICommand {
    @Override
    public void execute(Context context, String[] args) {
        log.info("Quit command executed");
        System.exit(0);
    }
}
