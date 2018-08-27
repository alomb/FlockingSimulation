package flocking.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The simulation environment.
 */
public class Simulation implements Model {

    private final List<Entity> entities;

    /**
     * Initialize variables.
     */
    public Simulation() {
        entities = new ArrayList<>();
        recalculate();
    }

    @Override
    public final void update(final float elapsed) {
        this.entities.forEach(e -> e.update(elapsed));
    }

    @Override
    public final List<Entity> getFigures() {
        return this.entities;
    }

    @Override
    public final void recalculate() {
        entities.clear();
    }

    @Override
    public final void createEntity() {
        final int sideLength = 10;
        final int maxSize = 700;
        final int speed = 50;

        this.entities.add(new EntityImpl(new Vector2DImpl(new Random().nextInt(maxSize),
                new Random().nextInt(maxSize)),
                sideLength,
                speed));
    }

}
