package flocking.controller;

import flocking.controller.input.Command;
import flocking.model.Model;

/**
 * An interface for a generic loop.
 */
public interface Loop {

    /**
     * @param model the logic behind
     */
    void setup(Model model);

    /**
     * The game loop based on cycles elapsed time that manage game and graphic updates.
     */
    void mainLoop();

    /**
     * Stop the loop.
     */
    void stop();

    /**
     * Pause the loop.
     */
    void pause();

    /**
     * Resume the loop.
     */
    void resume();

    /**
     * @param command a command to execute on the logic
     */
    void notifyCommand(Command command);
}
