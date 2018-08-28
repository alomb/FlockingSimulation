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
     * @return the entity's rotation angle
     */
    double getAngle();
}
