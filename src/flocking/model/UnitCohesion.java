package flocking.model;

import java.util.List;

/**
 * An {@link Unit} decorator used to perform the cohesion rule.
 */
public class UnitCohesion extends UnitDecorator implements Unit {

    private final Unit unit;

    private static final double MAX_COHESION = 60;
    private static final double MIN_COHESION_DISTANCE = 10;

    /**
     * @param unit the base of this decorator
     */
    public UnitCohesion(final Unit unit) {
        super(unit);
        this.unit = unit;
    }

    @Override
    public final Vector2D getSteeringForce() {
        return new Vector2DImpl(this.unit.getSteeringForce().sumVector(this.cohesion()));
    }

    /**
     * @return the cohesion rule steering force
     */
    private Vector2D cohesion() {
        final List<Entity> neighbors = Simulation.getNeighbors(this.getCohesionArea(), this);
        if (neighbors.isEmpty()) {
            return new Vector2DImpl(0, 0);
        }

        double counter = 0;
        Vector2D centroid = new Vector2DImpl(0, 0);
        for (final Entity n : neighbors) {
            if (n.getPosition().distance(this.getPosition()) > UnitCohesion.MIN_COHESION_DISTANCE) {
                centroid = centroid.sumVector(n.getPosition());
                counter++;
            }
        }

        if (counter != 0) {
            centroid = centroid.mulScalar(1 / counter);
            final Vector2D cohesionForce = centroid.sumVector(this.getPosition().mulScalar(-1));
            return cohesionForce.normalize().mulScalar(UnitCohesion.MAX_COHESION);
        }

        return new Vector2DImpl(0, 0);
    }

}
