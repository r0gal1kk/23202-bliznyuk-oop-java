package commands;

import context.Context;

import java.util.List;
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
    public void execute(Context context, List<String> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("Define command requires 2 arguments");
        }
        String name;
        double value;
        try {
            name = args.getFirst();
            if (!isCorrectName(name)) {
                throw new IllegalArgumentException("Invalid variable name: " + name);
            }
            value = Double.parseDouble(args.get(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number: " + args.get(1));
        }
        context.defineVariable(name, value);
    }
}
