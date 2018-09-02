package flocking.model.unit;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import flocking.model.Entity;
import flocking.model.Simulation;
import flocking.model.Vector2D;
import flocking.model.Vector2DImpl;

/**
 * An {@link Unit} decorator used to perform the separation rule.
 */
public class UnitSeparation extends UnitDecorator implements Unit {

    private final Unit unit;

    private static final double MAX_SEPARATION_DISTANCE = 15;
    private static final double MAX_SEPARATION = 120;

    /**
     * @param unit the base of this decorator
     */
    public UnitSeparation(final Unit unit) {
        super(unit);
        this.unit = unit;
    }

    @Override
    public final Vector2D getSteeringForce() {
        return new Vector2DImpl(this.unit.getSteeringForce().sumVector(this.separate()));
    }

    /**
     * @return the separation rule steering force
     */
    private Vector2D separate() {
        final List<Unit> neighbors = Simulation.getNeighbors(this.getCohesionArea(), this);
        if (neighbors.isEmpty()) {
            return new Vector2DImpl(0, 0);
        }

        Vector2D separationForce = new Vector2DImpl(0, 0);
        for (final Entity n : neighbors) {
            if (n.getPosition().distance(this.getPosition()) < UnitSeparation.MAX_SEPARATION_DISTANCE) {
                separationForce = separationForce.sumVector((n.getPosition().sumVector(this.getPosition().mulScalar(-1))).mulScalar(-1));
            }
        }

        try {
            return separationForce.normalize().mulScalar(UnitSeparation.MAX_SEPARATION);
        } catch (OperationNotSupportedException e) {
            return new Vector2DImpl(0, 0);
        }
    }
}
