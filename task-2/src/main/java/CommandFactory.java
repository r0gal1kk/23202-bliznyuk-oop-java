import commands.ICommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class CommandFactory {
    private static final Logger log = LoggerFactory.getLogger(CommandFactory.class);
    private final Map<String, ICommand> commands = new ConcurrentHashMap<>();
    private static final String CONFIG_FILE = "commands.properties";

    public CommandFactory() {
        loadCommands();
    }

    private void loadCommands() {
        Properties props = new Properties();
        try (InputStream input = CommandFactory.class.getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                log.error("Configuration file {} not found", CONFIG_FILE);
                throw new IOException("Configuration file " + CONFIG_FILE + " not found");
            }
            props.load(input);

            for (String commandName : props.stringPropertyNames()) {
                String className = props.getProperty(commandName);
                try {
                    ICommand command = (ICommand) Class.forName(className).getDeclaredConstructor().newInstance();
                    commands.put(commandName.toUpperCase(), command);
                    log.info("Loaded command: {} -> {}", commandName, className);
                } catch (ClassNotFoundException e) {
                    log.error("Command class {} not found", className, e);
                } catch (InstantiationException | IllegalAccessException e) {
                    log.error("Failed to instantiate command class {}", className, e);
                } catch (InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            log.error("Failed to load configuration file {}", CONFIG_FILE, e);
        }
    }

    public ICommand getCommand(String commandName) {
        return commands.get(commandName.toUpperCase());
    }
}