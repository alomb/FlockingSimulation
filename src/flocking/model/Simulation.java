package flocking.model;

import java.awt.Point;
import java.awt.Shape;
import java.awt.Toolkit;
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
        final int sideLength = 8;
        final int speed = 12;
        final Random rnd = new Random();

        Simulation.ENTITIES.add(new EntityImpl(new Vector2DImpl(rnd.nextInt((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()),
                rnd.nextInt((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight())),
                sideLength,
                new Vector2DImpl(rnd.nextBoolean() ? speed : -speed,
                        rnd.nextBoolean() ? speed : -speed)));
    }

    /**
     * @param neighborsArea the {@link Shape} to check entities within
     * @param entity the {@link Entity} which call this 
     * @return the {@link List} of {@link Entity} within the area
     */
    public static List<Entity> getNeighbors(final Shape neighborsArea, final Entity entity) {
        return Simulation.ENTITIES.stream().filter(e -> {
            //System.out.println(new Point((int) Math.round(e.getPosition().getX()),
                    //((int) Math.round(e.getPosition().getY()))));
            return neighborsArea.contains(new Point((int) Math.round(e.getPosition().getX()),
                    ((int) Math.round(e.getPosition().getY())))) && !e.equals(entity);
        }).collect(Collectors.toList());
    }

}
