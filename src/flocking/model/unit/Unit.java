package flocking.model.unit;

import java.awt.Shape;

import flocking.model.Entity;
import flocking.model.Vector2D;

/**
 * An extension of {@link Entity} to model the member of a flock.
 */
public interface Unit extends Entity {

    /**
     * @return the figure side length
     */
    int getSideLength();

    /**
     * @return a {@link Shape} of the cohesion influence area
     */
    Shape getCohesionArea();

    /**
     * @return a {@link Vector2D} representing the speed
     */
    Vector2D getSpeed();

    /**
     * @param speed the new speed
     */
    void setSpeed(Vector2D speed);

    /**
     * @return the entity's rotation angle
     */
    double getAngle();

    /**
     * @param degrees the new rotation angle
     */
    void setAngle(double degrees);

    /**
     * Set wander or target mode.
     */
    void toogleWander();

    /**
     * @return the calculated steering force to move the unit
     */
    Vector2D getSteeringForce();

    /**
     * @return the {@link Unit} mass
     */
    double getMass();

    /**
     * @return the unit current timer value
     */
    float getTimer();

    /**
     * @param timer the new current timer value
     */
    void setTimer(float timer);

    /**
     * Adjust the position to link opposite window sides.
     */
    void adjustPosition();

}
