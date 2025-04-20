import commands.ICommand;
import context.Context;
import java.util.Arrays;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Calculator {
    private static final Logger log = LoggerFactory.getLogger(Calculator.class);
    private final Context context;
    private final CommandFactory factory;

    public Calculator() {
        context = new Context();
        factory = new CommandFactory();
    }

    public void process(String[] args) {
        log.info("Starting Calculator");
        String buffer;
        try (Scanner scanner = args.length == 0 ?
                new Scanner(new InputStreamReader(System.in)) : new Scanner(new FileReader(args[0]))) {
            while (scanner.hasNextLine()) {
                buffer = scanner.nextLine().trim();
                if (buffer.isEmpty() || buffer.startsWith("#")) {
                    continue;
                }
                String[] tokens = buffer.split(" ");
                String commandName = tokens[0].toUpperCase();
                String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
                log.info("Got command: {} {}", commandName, params);
                ICommand command = factory.getCommand(commandName);
                if (command == null) {
                    log.error("Unknown command: {}", commandName);
                    continue;
                }
                try {
                    command.execute(context, params);
                    log.info("Command {} executed successfully", commandName);
                }
                catch (Exception e) {
                    log.error("Error executing command: {}", commandName, e);
                }
            }
        }
        catch (IOException e) {
            log.error("Error reading file", e);
        }
    }
}
