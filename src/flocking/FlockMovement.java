package flocking;

import flocking.controller.Controller;
import flocking.controller.ControllerImpl;
import flocking.view.View;
import flocking.view.ViewImpl;

/**
 * The application main class.
 */
public final class FlockMovement {

    private FlockMovement() {

    }

    /**
     * @param args the parameters passed via command line
     */
    public static void main(final String[] args) {
        final View view = new ViewImpl();
        final Controller controller = new ControllerImpl(view);
        controller.setup();
    }
}
