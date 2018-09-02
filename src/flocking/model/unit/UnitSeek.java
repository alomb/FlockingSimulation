package flocking.model.unit;

import javax.naming.OperationNotSupportedException;

import flocking.model.Simulation;
import flocking.model.Vector2D;
import flocking.model.Vector2DImpl;

/**
 * An {@link Unit} decorator used to perform the collision avoidance.
 */
public class UnitSeek extends UnitDecorator implements Unit {

    private final Unit unit;

    private static final double MAX_SEEK = UnitBase.MAX_SPEED;

    /**
     * @param unit the base of this decorator
     */
    public UnitSeek(final Unit unit) {
        super(unit);
        this.unit = unit;
    }

    @Override
    public final Vector2D getSteeringForce() {
        return new Vector2DImpl(this.seek().sumVector(this.unit.getSteeringForce()));
    }


    /**
     * @return the seek steering forces
     */
    private Vector2D seek() {
        final Vector2D target = new Vector2DImpl(Simulation.TARGET.getPosition().getX(), 
                Simulation.TARGET.getPosition().getY());

        try {
            return (((target.sumVector(this.getPosition().mulScalar(-1))).normalize()).mulScalar(UnitSeek.MAX_SEEK)).sumVector(this.getSpeed().mulScalar(-1));
        } catch (OperationNotSupportedException e) {
            return new Vector2DImpl(0, 0);
        }
    }

}
