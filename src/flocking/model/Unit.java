package flocking.model;

import java.awt.Shape;

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

    void setSpeed(Vector2D speed);

    /**
     * @return the entity's rotation angle
     */
    double getAngle();

    /**
     * Set wander or target mode.
     */
    void toogleWander();

    /**
     * @return the calculated steering force to move the unit
     */
    Vector2D getSteeringForce();

    double getMass();

    float getTimer();

    void setTimer(float timer);

    void setAngle(double degrees);

    /**
     * Adjust the position to link opposite window sides.
     */
    void adjustPosition();

}
