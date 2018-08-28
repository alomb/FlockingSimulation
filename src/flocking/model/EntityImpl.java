package flocking.model;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An implementation of {@link Entity}.
 */
public class EntityImpl implements Entity {

    private Vector2D position;
    private double angle;
    private Vector2D speed;

    private int timer;
    private static final int MAX_TIMER = 500;

    private static final double MIN_ANGLE = 135;
    private static final double MAX_ANGLE = -270;
    private static final int DELTA_ANGLE = 45;

    private static final int AREA = 20;
    private static final double MAX_FORCE = 25;
    private static final double MAX_SPEED = 20;

    private final int sideLength;
    private List<Vector2D> figure;
    private final double mass;

    /**
     * @param startPos the first {@link Entity}'s {@link Point}
     * @param sideLength the {@link Entity} length
     * @param speed the {@link Entity} speed
     */
    public EntityImpl(final Vector2D startPos, final int sideLength, final Vector2D speed) {
        this.position = new Vector2DImpl(startPos);
        this.speed = new Vector2DImpl(speed);
        this.angle = Math.toDegrees(Math.atan2(speed.getY(), speed.getX()));
        this.mass = 10;

        this.timer = EntityImpl.MAX_TIMER;

        this.sideLength = sideLength;
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
            Vector2D result = new Vector2DImpl(0, 0);
            result = result.sumVector(this.wander());
            result = result.clampMagnitude(EntityImpl.MAX_FORCE);
            result = result.mulScalar(1 / this.mass);

            System.out.println(this.speed.sumVector(result).lenght());
            final Vector2D finalSpeed = this.speed.sumVector(result).clampMagnitude(EntityImpl.MAX_SPEED);
            this.position = this.position.sumVector(finalSpeed);

            this.speed = this.speed.setAngle(angle);

            this.timer = EntityImpl.MAX_TIMER;
        } else {
            this.timer -= elapsed;
        }
    }

    private Vector2D wander() {
        final Random rnd = new Random();
        double deltaAngle = rnd.nextInt(EntityImpl.DELTA_ANGLE);
        if (rnd.nextBoolean()) {
            deltaAngle = -rnd.nextInt(EntityImpl.DELTA_ANGLE);
        }

        Vector2D center = new Vector2DImpl(this.speed);
        center = center.normalize().mulScalar(this.sideLength * 2);

        Vector2D displacement = new Vector2DImpl(0, -1);
        displacement = displacement.mulScalar(this.sideLength);

        displacement = displacement.rotate(deltaAngle);

        angle += deltaAngle;
        if (angle < 0) {
            angle += 360;
        } else if (angle > 360) {
            angle -= 360;
        }

        return displacement.sumVector(center);
    }

    @Override
    public final Shape getCohesionArea() {
        final Rectangle rectangle = new Rectangle((int) this.position.getX() - sideLength * AREA / 2,
                (int) this.position.getY() - sideLength * AREA / 2,
                sideLength * AREA,
                sideLength * AREA);

        final Shape arc = new Arc2D.Double(rectangle.x,
                rectangle.y, 
                rectangle.width, 
                rectangle.height, 
                MIN_ANGLE, 
                MAX_ANGLE, 
                Arc2D.PIE);

        final Path2D.Double path = new Path2D.Double();
        path.append(arc, false);

        final AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(angle), this.position.getX(), this.position.getY());
        path.transform(at);

        return path;
    }
}
