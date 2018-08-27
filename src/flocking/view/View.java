package flocking.view;

import flocking.controller.Controller;

/**
 * The responsible of rendering and scene managing.
 */
public interface View {

    /**
     * Method used to initialize the view.
     * @param controller the application controller
     */
    void initialize(Controller controller);

    /**
     * Render the current {@link Scene}.
     */
    void render();

}
