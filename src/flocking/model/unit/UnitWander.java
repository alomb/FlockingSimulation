package flocking.model.unit;

import java.util.Random;

import flocking.model.Vector2D;
import flocking.model.Vector2DImpl;

/**
 * The base Unit implementation used by decorators.
 */
public class UnitWander extends UnitDecorator implements Unit {

    private final Unit unit;

    private static final int DELTA_ANGLE = 50;

    /**
     * @param unit the base of this decorator
     */
    public UnitWander(final Unit unit) {
        super(unit);
        this.unit = unit;
    }

    @Override
    public final Vector2D getSteeringForce() {
        return new Vector2DImpl(this.wander().sumVector(this.unit.getSteeringForce()));
    }

    /**
     * @return the wander steering forces
     */
    private Vector2D wander() {
        final Random rnd = new Random();
        double deltaAngle = rnd.nextInt(UnitWander.DELTA_ANGLE) + DELTA_ANGLE / 3;
        if (rnd.nextBoolean()) {
            deltaAngle = -rnd.nextInt(UnitWander.DELTA_ANGLE) - DELTA_ANGLE / 3;
        }

        Vector2D center = new Vector2DImpl(this.getSpeed());
        center = center.normalize().mulScalar(this.getSideLength() * 2);

        center = center.rotate(deltaAngle);

        return center;
    }
}
