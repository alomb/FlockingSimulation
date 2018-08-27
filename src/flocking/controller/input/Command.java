package flocking.controller.input;

import flocking.model.Model;

/**
 * A command object invokes methods of the receiver (the object affected by the execution of certain commands)
 * and/or do other operations when an invoker (e.g. the keyboard) demands it by calling the execute method.
 * It provides a simple way of delegating some operations to other classes.
 */
public interface Command {

    /**
     * @param model the reference to the current model logic
     */
    void execute(Model model);
}
