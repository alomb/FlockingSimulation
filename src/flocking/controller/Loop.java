package flocking.controller;

import flocking.controller.input.Command;
import flocking.model.Model;
import flocking.view.View;

/**
 * An interface for a generic loop.
 */
public interface Loop {

    /**
     * @param model the {@link Model} to update
     * @param scene the {@link View} to render
     * The method used to initialize the game loop. 
     */
    void setup(Model model, View scene);

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
