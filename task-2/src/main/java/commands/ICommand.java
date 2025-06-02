package commands;
import context.Context;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.util.List;

public interface ICommand {
    Logger log = LoggerFactory.getLogger(ICommand.class);
    void execute(Context context, List<String> args);
}
