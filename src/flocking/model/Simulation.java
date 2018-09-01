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

import flocking.controller.Controller;
import flocking.view.ViewImpl;

/**
 * The simulation environment.
 */
public class Simulation implements Model {

    private final Controller controller;

    private static final List<Unit> UNITS = new ArrayList<>();
    private static final List<Entity> OBSTACLES = new ArrayList<>();

    private String commandFeedback = "";
    private int timerFeedback;

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
        this.wander = true;
        this.reset();
    }

    @Override
    public final void update(final float elapsed) {
        Simulation.UNITS.forEach(e -> e.update(elapsed));
        if (!this.wander) {
            TARGET.update(elapsed);
        }

        if (this.timerFeedback > 0) {
            this.timerFeedback -= elapsed;
        } 

        if (this.commandFeedback.length() > 0 && this.timerFeedback <= 0) {
            this.timerFeedback = 0;
            this.commandFeedback = "";
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
    public final void executeCommands(final String command) {
        int repeat = 0, i = 0;
        String filteredCommand = String.copyValueOf(command.toCharArray());
        while (command.length() > i && Character.isDigit(command.charAt(i))) {
            i++;
        }

        if (i > 0) {
            repeat = Integer.parseInt(command.substring(0, i));
        }

        filteredCommand = command.substring(i);

        if (filteredCommand.length() > 0) {
            switch (filteredCommand.charAt(0)) {
            case 'c' : 
                if (filteredCommand.length() == 2 && filteredCommand.charAt(1) == 'e') {
                    //Creates entities
                    for (int r = 0; r < repeat; r++) {
                        this.createEntity();
                    }
                    this.commandFeedback = "CREATED " + repeat + " ENTITIES";
                } else if (filteredCommand.length() == 2 && filteredCommand.charAt(1) == 'o') {
                    //Creates obstacles
                    for (int r = 0; r < repeat; r++) {
                        this.createObstacle();
                    }
                    this.commandFeedback = "CREATED " + repeat + " OBSTACLES";
                }
                break;
            case 'p' : 
                if (filteredCommand.length() == 1) {
                    this.controller.pause();
                    this.commandFeedback = "PAUSE-RESUME";
                }
                break;
            case 'r' : 
                if (filteredCommand.length() == 1) {
                    this.reset();
                    this.commandFeedback = "RESET";
                }
                break;
            case 'w' : 
                if (filteredCommand.length() == 1) {
                    this.wander = !this.wander;
                    Simulation.UNITS.forEach(u -> u.toogleWander());
                    this.commandFeedback = "WANDER " + (this.wander ? "ENABLED" : "DISABLED");
                }
                break;
            default:
                this.commandFeedback = "COMMAND NOT FOUND";
                break;
            }

            this.timerFeedback = 1000;
        }
    }

    @Override
    public final String getCommandFeedback() {
        return this.commandFeedback;
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

    /**
     * Clean the scene, deleting all obstacles and units.
     */
    private void reset() {
        Simulation.UNITS.clear();
        Simulation.OBSTACLES.clear();
    }

    /**
     * Create an entity.
     */
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
                rnd.nextInt((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - ViewImpl.TEXT_HEIGHT / 2)),
                sideLength,
                new Vector2DImpl(rnd.nextBoolean() ? speed : -speed,
                        rnd.nextBoolean() ? speed : -speed)));

    }

    /**
     * Create an obstacle.
     */
    private void createObstacle() {

        final int y = ViewImpl.HEIGHT - (ViewImpl.TEXT_HEIGHT) - (ViewImpl.HEIGHT / 4);
        final int x = ViewImpl.WIDTH - ViewImpl.WIDTH / 5;
        final int maxSize = 30;

        final Random rnd = new Random();
        Simulation.OBSTACLES.add(new Obstacle(new Vector2DImpl(rnd.nextInt(x) + ViewImpl.WIDTH / 10,
            rnd.nextInt(y) + ViewImpl.HEIGHT / 8), maxSize));
    }
}
