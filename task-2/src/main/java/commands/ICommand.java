package commands;
import context.Context;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public interface ICommand {
    Logger log = LoggerFactory.getLogger(ICommand.class);
    void execute(Context context, String[] args);
}
