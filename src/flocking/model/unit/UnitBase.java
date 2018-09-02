package flocking.model.unit;

import flocking.model.Vector2D;
import flocking.model.Vector2DImpl;

/**
 * The base Unit implementation used by decorators.
 */
public class UnitBase extends UnitImpl implements Unit {

    /**
     * @param startPos the first {@link Unit}'s {@link Point}
     * @param sideLength the {@link Unit} side length
     * @param speed the {@link Unit} speed
     */
    public UnitBase(final Vector2D startPos, final int sideLength, final Vector2D speed) {
        super(startPos, sideLength, speed);
    }

    @Override
    public final Vector2D getSteeringForce() {
        return new Vector2DImpl(0, 0);
    }
}
