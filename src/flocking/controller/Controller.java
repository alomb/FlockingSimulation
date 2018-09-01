package flocking.controller;

import java.util.List;

import flocking.controller.input.Command;
import flocking.model.Entity;

/**
 * The application controller.
 */
public interface Controller {

    /**
     * @param command the command to be notified to the loop engine
     */
    void notifyCommand(Command command);

    /**
     * Setup the application.
     */
    void setup();

    /**
     * @return the figures to be drawn
     */
    List<Entity> getFigures();

    /**
     * Pause the simulation.
     */
    void pause();

    /**
     * @return the {@link Model} homonym method
     */
    String getCommandFeedback();
}
