package flocking.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An implementation of {@link Entity}.
 */
public class EntityImpl implements Entity {

    private Vector2D position;
    private double angle;
    private final double speed;

    private int timer;
    private static final int MAX_TIMER = 500;
    private static final int MAX_ANGLE = 45;

    private final int sideLength;
    private List<Vector2D> figure;

    /**
     * @param startPos the first {@link Entity}'s {@link Point}
     * @param sideLength the {@link Entity} length
     * @param speed the {@link Entity} speed
     */
    public EntityImpl(final Vector2D startPos, final int sideLength, final double speed) {
        this.sideLength = sideLength;
        this.position = startPos;
        this.angle = 0;
        this.speed = speed;
        this.timer = EntityImpl.MAX_TIMER;

        this.figure = new ArrayList<>();
    }

    @Override
    public final List<Vector2D> getFigure() {
        this.figure.clear();
        this.figure.add(new Vector2DImpl(this.position.getX() - this.sideLength / 2, this.position.getY() - this.sideLength / 2));
        this.figure.add(new Vector2DImpl(this.position.getX() - this.sideLength / 2, this.position.getY() + this.sideLength / 2));
        this.figure.add(new Vector2DImpl(this.position.getX() + this.sideLength, this.position.getY()));
        return this.figure;
    }

    @Override
    public final void setFigure(final List<Vector2D> figure) {
        this.figure = figure;
    }

    @Override
    public final Vector2D getPosition() {
        return this.position;
    }

    @Override
    public final double getAngle() {
        return this.angle;
    }

    @Override
    public final void setPosition(final Vector2D position) {
        this.position = position;
    }

    @Override
    public final void update(final float elapsed) {
        if (this.timer <= 0) {
            final Random rnd = new Random();
            if (rnd.nextBoolean()) {
                angle += rnd.nextInt(EntityImpl.MAX_ANGLE);
            } else {
                angle -= rnd.nextInt(EntityImpl.MAX_ANGLE);
            }

            if (angle < 0) {
                angle += 360;
            } else if (angle > 360) {
                angle -= 360;
            }
            this.timer = EntityImpl.MAX_TIMER;

            final double angleRad = Math.toRadians(this.angle);

            final Vector2D targetPos = new Vector2DImpl(this.position.normalize().getX() * Math.cos(angleRad) * this.speed,
                    this.position.normalize().getY() * Math.sin(angleRad) * this.speed);
            this.position = this.position.sumVector(targetPos);

        } else {
            this.timer -= elapsed;
        }
    }
}
