package Domain.CommandProcessor;

public class CommandProcessor {
    public CommandProcessor() {
    }

    public void execute(Command command) {
        command.execute();
    }
}
