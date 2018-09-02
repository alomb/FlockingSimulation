package flocking.model.unit;

import java.util.List;

import flocking.model.Entity;
import flocking.model.Simulation;
import flocking.model.Vector2D;
import flocking.model.Vector2DImpl;

/**
 * An {@link Unit} decorator used to perform the alignment rule.
 */
public class UnitAlignment extends UnitDecorator implements Unit {

    private final Unit unit;

    private static final double MIN_ALIGNMENT_DISTANCE = 10;

    /**
     * @param unit the base of this decorator
     */
    public UnitAlignment(final Unit unit) {
        super(unit);
        this.unit = unit;
    }

    @Override
    public final Vector2D getSteeringForce() {
        return new Vector2DImpl(this.unit.getSteeringForce().sumVector(this.align()));
    }

    /**
     * @return the alignment rule steering force
     */
    private Vector2D align() {
        final List<Entity> neighbors = Simulation.getNeighbors(this.getCohesionArea(), this);
        if (neighbors.isEmpty()) {
            return new Vector2DImpl(0, 0);
        }

        double counter = 0;
        Vector2D averageSpeed = new Vector2DImpl(0, 0);
        for (final Entity n : neighbors) {
            if (n.getPosition().distance(this.getPosition()) > UnitAlignment.MIN_ALIGNMENT_DISTANCE) {
                averageSpeed = averageSpeed.sumVector(n.getPosition());
                counter++;
            }
        }

        if (counter != 0) {
            averageSpeed = averageSpeed.mulScalar(1 / counter);
            final Vector2D cohesionForce = averageSpeed.sumVector(this.getSpeed().mulScalar(-1));
            return cohesionForce.normalize().mulScalar(1f / 2f);
        }

        return new Vector2DImpl(0, 0);
    }
}
