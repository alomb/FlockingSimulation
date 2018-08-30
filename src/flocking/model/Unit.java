package flocking.model;

import java.awt.Shape;

/**
 * An extension of {@link Entity} to model the member of a flock.
 */
public interface Unit extends Entity {

    /**
     * @return a {@link Shape} of the cohesion influence area
     */
    Shape getCohesionArea();

    /**
     * @return a {@link Vector2D} representing the speed
     */
    Vector2D getSpeed();

    /**
     * @return the entity's rotation angle
     */
    double getAngle();

    /**
     * Set wander or target mode.
     */
    void toogleWander();
}
