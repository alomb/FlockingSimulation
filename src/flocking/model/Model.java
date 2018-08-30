package flocking.model;

import java.util.List;

/**
 * The generic model used to manage the environment.
 */
public interface Model {

    /**
     * @param elapsed the time elapsed in the loop cycle
     */
    void update(float elapsed);

    /**
     * @return the {@link Entity}'s figures
     */
    List<Entity> getFigures();

    /**
     * 
     */
    void recalculate();

    /**
     * @param command a string representing commands
     */
    void executeCommands(String command);

}
