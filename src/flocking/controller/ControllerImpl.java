package flocking.controller;

import java.util.List;

import flocking.controller.input.Command;
import flocking.model.Entity;
import flocking.model.Model;
import flocking.model.Simulation;
import flocking.view.View;

/**
 * An implementation of {@link Controller}.
 */
public class ControllerImpl implements Controller {

    private final Loop engine;
    private final Model model;
    private final View view;
    private boolean paused;

    /**
     * @param view the application {@link View}
     */
    public ControllerImpl(final View view) {
        this.engine = new Engine();
        this.model = new Simulation();
        this.view = view;
    }

    @Override
    public final void notifyCommand(final Command command) {
        this.engine.notifyCommand(command);
    }

    @Override
    public final void setup() {
        engine.setup(this.model, this.view);
        this.view.initialize(this);
        engine.mainLoop();
    }

    @Override
    public final List<Entity> getFigures() {
        return this.model.getFigures();
    }

    @Override
    public final void pause() {
        if (this.paused) {
            this.engine.resume();
        } else {
            this.engine.pause();
        }
        this.paused = !this.paused;
    }

}
