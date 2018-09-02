package flocking.model;

/**
 * An {@link Unit} decorator used to perform the collision avoidance.
 */
public class UnitSeek extends UnitImpl implements Unit {

    private static final double MAX_SEEK = 50;

    /**
     * @param startPos the first {@link Unit}'s {@link Point}
     * @param sideLength the {@link Unit} side length
     * @param speed the {@link Unit} speed
     */
    public UnitSeek(final Vector2D startPos, final int sideLength, final Vector2D speed) {
        super(startPos, sideLength, speed);
    }

    @Override
    public final Vector2D getSteeringForce() {
        System.out.println("S" + this.getPosition());
        return new Vector2DImpl(this.seek());
    }

    /**
     * @return the seek steering forces
     */
    private Vector2D seek() {
        final Vector2D target = new Vector2DImpl(Simulation.TARGET.getPosition().getX(), 
                Simulation.TARGET.getPosition().getY());

        return (((target.sumVector(this.getPosition().mulScalar(-1))).normalize()).mulScalar(UnitSeek.MAX_SEEK)).sumVector(this.getSpeed().mulScalar(-1));
    }

}
