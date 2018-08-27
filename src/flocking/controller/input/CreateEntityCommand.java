package flocking.controller.input;

import flocking.model.Model;

/**
 * A {@link Command} implementation to create a new {@link Entity}.
 */
public class CreateEntityCommand implements Command {

    @Override
    public final void execute(final Model model) {
        model.createEntity();
    }

}
