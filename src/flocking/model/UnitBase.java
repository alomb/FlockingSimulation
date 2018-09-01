package flocking.model;

import java.util.Random;

/**
 * The base Unit implementation used by decorators.
 */
public class UnitBase extends UnitImpl implements Unit {

    private static final int DELTA_ANGLE = 20;

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
        return new Vector2DImpl(this.wander());
    }

    /**
     * @return the wander steering forces
     */
    private Vector2D wander() {
        final Random rnd = new Random();
        double deltaAngle = rnd.nextInt(UnitBase.DELTA_ANGLE);
        if (rnd.nextBoolean()) {
            deltaAngle = -rnd.nextInt(UnitBase.DELTA_ANGLE);
        }

        Vector2D center = new Vector2DImpl(this.getSpeed());
        center = center.normalize().mulScalar(this.getSideLength() * 2);

        center = center.rotate(deltaAngle);

        return center;
    }
}
