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
import flocking.model.unit.Unit;
import flocking.model.unit.UnitAlignment;
import flocking.model.unit.UnitBase;
import flocking.model.unit.UnitCohesion;
import flocking.model.unit.UnitCollisionAvoidance;
import flocking.model.unit.UnitSeek;
import flocking.model.unit.UnitSeparation;
import flocking.model.unit.UnitWander;
import flocking.view.ViewImpl;

/**
 * The simulation environment.
 */
public class Simulation implements Model {

    private final Controller controller;

    private static List<Unit> units = new ArrayList<>();
    private static final List<Entity> OBSTACLES = new ArrayList<>();

    private String commandFeedback = "";
    private int timerFeedback;

    //commands
    private boolean wander;
    private boolean seek;

    /**
     * The target of the {@link Unit}s.
     */
    public static final Target TARGET = new Target();

    /**
     * @param controller the application {@link Controller}
     */
    public Simulation(final Controller controller) {
        this.controller = controller;

        this.reset();
        for (int i = 0; i < 100; i++) {
            Simulation.units.add(this.getUnit(this.getBaseUnit()));
        }
    }

    @Override
    public final void update(final float elapsed) {
        Simulation.units.forEach(e -> e.update(elapsed));
        if (this.seek) {
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
        entities.addAll(units);
        entities.addAll(OBSTACLES);
        if (this.seek) {
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
                        if (this.wander) {
                            Simulation.units.add(this.getWanderUnit(this.getUnit(this.getBaseUnit())));
                        } else if (this.seek) {
                            Simulation.units.add(this.getSeekUnit(this.getUnit(this.getBaseUnit())));
                        } else {
                            Simulation.units.add(this.getUnit(this.getBaseUnit()));
                        }
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
                    this.wander = false;
                    this.wander = seek;
                }
                break;
            case 'w' : 
                if (filteredCommand.length() == 1) {
                    if (!this.wander) {
                        i = 0;
                        final int oldSize = Simulation.units.size();
                        for (i = 0; i < oldSize; i++) {
                            Simulation.units.add(new UnitWander(this.getUnit(this.getBaseUnit(Simulation.units.get(i)))));
                        }

                        Simulation.units = Simulation.units.subList(oldSize, Simulation.units.size());
                    }

                    this.wander = true;
                    this.seek = false;

                    this.commandFeedback = "WANDER ENABLED";
                }
                break;
            case 's' : 
                if (filteredCommand.length() == 1) {
                    if (!this.seek) {
                        i = 0;
                        final int oldSize = Simulation.units.size();
                        for (i = 0; i < oldSize; i++) {
                            Simulation.units.add(new UnitSeek(this.getUnit(this.getBaseUnit(Simulation.units.get(i)))));
                        }

                        Simulation.units = Simulation.units.subList(oldSize, Simulation.units.size());
                    }

                    this.wander = false;
                    this.seek = true;

                    this.commandFeedback = "SEEK ENABLED";
                }
                break;
            case 'f' :
                if (filteredCommand.length() == 1) {
                    i = 0;
                    final int oldSize = Simulation.units.size();
                    for (i = 0; i < oldSize; i++) {
                        Simulation.units.add(this.getUnit(this.getBaseUnit(Simulation.units.get(i))));
                    }

                    Simulation.units = Simulation.units.subList(oldSize, Simulation.units.size());

                    this.wander = false;
                    this.seek = false;

                    this.commandFeedback = "ONLY FLOCK ENABLED";
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
    public static List<Unit> getNeighbors(final Shape neighborsArea, final Unit unit) {
        return Simulation.units.stream().filter(e -> {
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
        Simulation.units.clear();
        Simulation.OBSTACLES.clear();
    }

    /**
     * Create a unit with the three navigation rules and collision avoidance.
     */
    private Unit getUnit(final Unit unit) {

        return new UnitAlignment(
                new UnitSeparation(
                        new UnitCohesion(
                                new UnitCollisionAvoidance(unit))));
    }

    /**
     * @param unit the unit to copy
     * @return a copy of the passed {@link Unit}
     */
    private Unit getBaseUnit(final Unit unit) {
        return new UnitBase(unit.getPosition(), unit.getSideLength(), unit.getSpeed());
    }

    /**
     * @return a base {@link Unit}
     */
    private Unit getBaseUnit() {
        final int sideLength = 6;
        final int speed = 6;
        final Random rnd = new Random();

        return new UnitBase(new Vector2DImpl(rnd.nextInt((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()),
                rnd.nextInt((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - ViewImpl.TEXT_HEIGHT / 2)),
                sideLength,
                new Vector2DImpl(rnd.nextBoolean() ? speed : -speed,
                        rnd.nextBoolean() ? speed : -speed));
    }

    /**
     * @param unit the base unit
     * @return a seeking {@link Unit}
     */
    private Unit getSeekUnit(final Unit unit) {
        return new UnitSeek(unit);
    }

    /**
     * @param unit the base unit
     * @return a wandering {@link Unit}
     */
    private Unit getWanderUnit(final Unit unit) {
        return new UnitWander(unit);
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
