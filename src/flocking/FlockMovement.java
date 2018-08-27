package flocking;

import flocking.controller.Engine;
import flocking.controller.Loop;
import flocking.model.Model;
import flocking.model.Simulation;
import flocking.view.View;
import flocking.view.ViewImpl;

public final class FlockMovement {

	private FlockMovement() {
		
	}

	public static void main(final String[] args) {
		final Model model = new Simulation();
		final View scene = new ViewImpl(model);
		final Loop engine = new Engine(scene);
		engine.setup(model);
		engine.mainLoop();
	}
}
