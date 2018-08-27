package flocking.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Simulation implements Model {

	private final List<Entity> entities;

	public Simulation() {
		entities = new ArrayList<>();
		recalculate();
	}

	@Override
	public final void update(final float elapsed) {
        
	}

	@Override
	public final List<Entity> getFigures() {
		return this.entities;
	}

	@Override
	public final void recalculate() {
		final int sideLength = 10;
		final int maxSize = 700;
		entities.clear();
        IntStream.range(0, 500).forEach(i -> {
            entities.add(new EntityImpl(new Point(new Random().nextInt(maxSize), new Random().nextInt(maxSize)), sideLength));
	    });
	}

}
