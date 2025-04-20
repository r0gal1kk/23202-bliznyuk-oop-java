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
    private Context context;

    public void process(String[] args) {
        log.info("Starting Calculator");
        String buffer;
        try (Scanner scanner = args.length == 0 ?
                new Scanner(new InputStreamReader(System.in)) : new Scanner(new FileReader(args[0]))) {
            while (scanner.hasNextLine()) {
                buffer = scanner.nextLine().trim();
                String[] tokens = buffer.split(" ");
                String command = tokens[0].toUpperCase();
                String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
                log.info("Got command: {} {}", command, params);
            }
        }
        catch (IOException e) {
            log.error("Error reading file", e);
        }
    }
}
