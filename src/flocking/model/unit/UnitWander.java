package flocking.model.unit;

import java.util.Random;

import javax.naming.OperationNotSupportedException;

import flocking.model.Vector2D;
import flocking.model.Vector2DImpl;

/**
 * The base Unit implementation used by decorators.
 */
public class UnitWander extends UnitDecorator implements Unit {

    private final Unit unit;

    private static final double MAX_WANDER = (UnitBase.MAX_SPEED * 5) / 6;
    private static final int DELTA_ANGLE = 20;

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
        try {
            center = center.normalize().mulScalar(UnitWander.MAX_WANDER);
        } catch (OperationNotSupportedException e) {
            return new Vector2DImpl(0, 0);
        }

        center = center.rotate(deltaAngle);

        return center;
    }
}
