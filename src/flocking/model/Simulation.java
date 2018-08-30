package flocking.model;

import java.awt.Point;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import flocking.controller.Controller;

/**
 * The simulation environment.
 */
public class Simulation implements Model {

    private final Controller controller;

    private static final List<Unit> UNITS = new ArrayList<>();
    private static final List<Entity> OBSTACLES = new ArrayList<>();
    private static final int OBSTACLES_NUMBER = 50;
    private static final int ENTITIES_NUMBER = 500;

    //commands
    private boolean wander;

    /**
     * The target of the {@link Unit}s.
     */
    public static final Target TARGET = new Target();

    /**
     * @param controller the application {@link Controller}
     */
    public Simulation(final Controller controller) {
        this.controller = controller;
        recalculate();
        this.setObstacles();
        //Random obstacles
        IntStream.range(0, Simulation.ENTITIES_NUMBER).forEach(i -> {
            this.createEntity();
        });
    }

    @Override
    public final void update(final float elapsed) {
        Simulation.UNITS.forEach(e -> e.update(elapsed));
        if (!this.wander) {
            TARGET.update(elapsed);
        }
    }

    @Override
    public final List<Entity> getFigures() {
        final List<Entity> entities = new ArrayList<>();
        entities.addAll(UNITS);
        entities.addAll(OBSTACLES);
        if (!this.wander) {
            entities.add(TARGET);
        }
        return Collections.unmodifiableList(entities);
    }

    @Override
    public final void recalculate() {
        Simulation.UNITS.clear();
    }

    @Override
    public final void executeCommands(final String command) {
        switch (command) {
        case "c" : 
            this.createEntity();
            break;
        case "s" : 
            this.controller.pause();
            break;
        case "w" : 
            this.wander = !this.wander;
            Simulation.UNITS.forEach(u -> u.toogleWander());
            break;
        default:
            break;
        }
    }

    /**
     * @param neighborsArea the {@link Shape} to check entities within
     * @param unit the {@link Unit} which call this 
     * @return the {@link List} of {@link Entity} within the area
     */
    public static List<Entity> getNeighbors(final Shape neighborsArea, final Unit unit) {
        return Simulation.UNITS.stream().filter(e -> {
            return neighborsArea.contains(new Point((int) Math.round(e.getPosition().getX()),
                    ((int) Math.round(e.getPosition().getY())))) && !e.equals(unit);
        }).collect(Collectors.toList());
    }

    /**
     * @param sight the {@link Line2D} to check obstacles
     * @param unit the {@link Unit} which call this 
     * @return the {@link List} of {@link Entity} intersected with the sight
     */
    public static List<Entity> getObstacleInPath(final Line2D.Double sight, final Unit unit) {
        return Simulation.OBSTACLES.stream().filter(e -> {
            return sight.intersects(e.getArea(1)) 
                    || e.getArea(1).contains(new Point((int) Math.round(unit.getPosition().getX()), 
                            (int) Math.round(unit.getPosition().getY())));
        }).collect(Collectors.toList());
    }

    private void createEntity() {
        final int sideLength = 6;
        final int speed = 6;
        final Random rnd = new Random();

        /*
        Simulation.UNITS.add(new UnitImpl(new Vector2DImpl(0,
                rnd.nextInt((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight())),
                sideLength,
                new Vector2DImpl(10, 0)));
        */
        Simulation.UNITS.add(new UnitImpl(new Vector2DImpl(rnd.nextInt((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()),
                rnd.nextInt((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight())),
                sideLength,
                new Vector2DImpl(rnd.nextBoolean() ? speed : -speed,
                        rnd.nextBoolean() ? speed : -speed)));

    }

    private void setObstacles() {

        final int y = (int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        final int x = (int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
        final int maxSize = 30;

        //Boundary obstacles
        Simulation.OBSTACLES.add(new Obstacle(new Vector2DImpl(0, y / 2), maxSize, y));
        Simulation.OBSTACLES.add(new Obstacle(new Vector2DImpl(x / 2, 0), x, maxSize));
        Simulation.OBSTACLES.add(new Obstacle(new Vector2DImpl(x / 2, y - maxSize), x, maxSize));
        Simulation.OBSTACLES.add(new Obstacle(new Vector2DImpl(x, y / 2), maxSize, y));

        //Random obstacles
        IntStream.range(0, Simulation.OBSTACLES_NUMBER).forEach(i -> {
            final Random rnd = new Random();
            Simulation.OBSTACLES.add(new Obstacle(new Vector2DImpl(rnd.nextInt(x),
                rnd.nextInt(y)), maxSize));
        });
    }
}
