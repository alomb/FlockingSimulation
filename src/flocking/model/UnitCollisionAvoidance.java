package flocking.model;

import java.util.List;
import java.util.Optional;

/**
 * An {@link Unit} decorator used to perform the collision avoidance.
 */
public class UnitCollisionAvoidance extends UnitImpl implements Unit {

    private final Unit unit;

    private static final double MAX_SIGHT = 15;
    private static final double MAX_AVOIDANCE = 200;

    /**
     * @param unit the base of this decorator
     */
    public UnitCollisionAvoidance(final Unit unit) {
        super(unit.getPosition(), unit.getSideLength(), unit.getSpeed());
        this.unit = unit;
    }

    @Override
    public final Vector2D getSteeringForce() {
        return new Vector2DImpl(this.unit.getSteeringForce().sumVector(this.obstacleAvoidance()));
    }

    /**
     * @return the collision avoidance steering force
     */
    private Vector2D obstacleAvoidance() {
        final List<Entity> obstacles = Simulation.getObstacleInPath(this.getLine(), this);
        if (obstacles.isEmpty()) {
            return new Vector2DImpl(0, 0);
        }

        final Optional<Entity> obstacle = obstacles.stream().min((o1, o2) -> {
            return o1.getPosition().distance(this.getPosition()) > o2.getPosition().distance(this.getPosition()) 
                    ? 1 : o1.getPosition().distance(this.getPosition()) == o2.getPosition().distance(this.getPosition()) 
                    ? 0 : -1;
        });

        if (!obstacle.isPresent()) {
            return new Vector2DImpl(0, 0);
        }

        final Vector2D sight = this.getSpeed().normalize().mulScalar(UnitCollisionAvoidance.MAX_SIGHT).sumVector(this.getPosition());
        final Vector2D avoidanceForce = sight.sumVector(obstacle.get().getPosition().mulScalar(-1));
        return avoidanceForce.normalize().mulScalar(UnitCollisionAvoidance.MAX_AVOIDANCE);
    }
}
