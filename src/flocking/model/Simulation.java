package flocking.model;

import java.awt.Point;
import java.awt.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * The simulation environment.
 */
public class Simulation implements Model {

    private static final List<Entity> ENTITIES = new ArrayList<>();

    /**
     * Initialize variables.
     */
    public Simulation() {
        recalculate();
    }

    @Override
    public final void update(final float elapsed) {
        Simulation.ENTITIES.forEach(e -> e.update(elapsed));
    }

    @Override
    public final List<Entity> getFigures() {
        return Simulation.ENTITIES;
    }

    @Override
    public final void recalculate() {
        Simulation.ENTITIES.clear();
    }

    @Override
    public final void createEntity() {
        final int sideLength = 10;
        final int maxSize = 700;
        final int speed = 50;

        Simulation.ENTITIES.add(new EntityImpl(new Vector2DImpl(new Random().nextInt(maxSize),
                new Random().nextInt(maxSize)),
                sideLength,
                speed));
    }

    public static List<Entity> getNeighbors(final Shape neighborsArea, final Entity entity) {
        return Simulation.ENTITIES.stream().filter(e -> {
            //System.out.println(new Point((int) Math.round(e.getPosition().getX()),
                    //((int) Math.round(e.getPosition().getY()))));
            return neighborsArea.contains(new Point((int) Math.round(e.getPosition().getX()),
                    ((int) Math.round(e.getPosition().getY())))) && !e.equals(entity);
        }).collect(Collectors.toList());
    }

}
