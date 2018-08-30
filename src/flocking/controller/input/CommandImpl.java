package flocking.controller.input;

import flocking.model.Model;

/**
 * A {@link Command} implementation to create a new {@link Entity}.
 */
public class CommandImpl implements Command {

    private final String command;

    /**
     * @param command the command {@link String}
     */
    public CommandImpl(final String command) {
        this.command = command;
    }

    @Override
    public final void execute(final Model model) {
        model.executeCommands(this.command);
    }

}

