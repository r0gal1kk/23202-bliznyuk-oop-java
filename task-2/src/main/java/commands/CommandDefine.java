package commands;

import context.Context;
import java.util.regex.Pattern;

public class CommandDefine implements ICommand {
    private final Pattern VAR_NAME_PATTERN = Pattern.compile("^[a-zA-Z_$][a-zA-Z0-9_$]*$");

    private boolean isCorrectName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        return VAR_NAME_PATTERN.matcher(name).matches();
    }

    @Override
    public void execute(Context context, String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Define command requires 2 arguments");
        }
        String name;
        double value;
        try {
            name = args[0];
            if (!isCorrectName(name)) {
                throw new IllegalArgumentException("Invalid variable name: " + name);
            }
            value = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number: " + args[1]);
        }
        context.defineVariable(name, value);
    }
}
