package flocking.model.unit;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import flocking.model.Vector2D;
import flocking.model.Vector2DImpl;
import flocking.view.ViewImpl;

/**
 * A basic implementation of {@link Unit}. The concrete class of the decoration pattern.
 */
public class UnitBase implements Unit {

    //Physics variables
    private Vector2D position;
    private double angle;
    private Vector2D speed;
    private final double mass;

    //Updating variables
    private float timer;
    /**
     * Max timer value.
     */
    public static final int MAX_TIMER = 400;

    //Rotation variables
    //150 , -295 :: 30 , -330 :: 90 , -270 :: 0 , -360
    private static final double MIN_ANGLE = 150;
    private static final double MAX_ANGLE = -295;

    //Unit view sizes
    private static final int AREA = 20;

    //Max values
    /**
     * Max resulting steering force {@link Vector2D} length.
     */
    public static final double MAX_FORCE = 200;
    /**
     * Max speed {@link Vector2D} length.
     */
    public static final double MAX_SPEED = 200;

    //Figure sizes and positions
    private final int sideLength;
    private final List<Vector2D> figure;

    /**
     * @param startPos the first {@link Unit}'s {@link Point}
     * @param sideLength the {@link Unit} side length
     * @param speed the {@link Unit} speed
     */
    public UnitBase(final Vector2D startPos, final int sideLength, final Vector2D speed) {
        this.position = new Vector2DImpl(startPos);
        this.speed = new Vector2DImpl(speed);
        this.angle = Math.toDegrees(Math.atan2(speed.getY(), speed.getX()));
        this.mass = 10;

        this.timer = UnitBase.MAX_TIMER;

        this.sideLength = sideLength;
        this.figure = new ArrayList<>();
    }

    @Override
    public final List<Vector2D> getFigure() {
        this.figure.clear();
        this.figure.add(new Vector2DImpl(this.position.getX() - this.sideLength / 2, this.position.getY() - this.sideLength / 2));
        this.figure.add(new Vector2DImpl(this.position.getX() - this.sideLength / 2, this.position.getY() + this.sideLength / 2));
        this.figure.add(new Vector2DImpl(this.position.getX() + this.sideLength, this.position.getY()));
        return Collections.unmodifiableList(this.figure);
    }

    @Override
    public final int getSideLength() {
        return this.sideLength;
    }

    @Override
    public final void setFigure(final List<Vector2D> figure) {
        Collections.copy(this.figure, figure);
    }

    @Override
    public final Vector2D getPosition() {
        return this.position;
    }

    @Override
    public final void setPosition(final Vector2D position) {
        this.position = new Vector2DImpl(position);
    }

    @Override
    public final double getAngle() {
        return this.angle;
    }

    @Override
    public final void setAngle(final double degrees) {
        this.angle = degrees;
    }

    @Override
    public final void update(final float elapsed) {
        if (this.timer <= 0) {
            Vector2D result = this.getSteeringForce();
            result = result.clampMagnitude(UnitBase.MAX_FORCE);
            result = result.mulScalar(1 / this.mass);

            final Vector2D finalSpeed = this.speed.sumVector(result).clampMagnitude(UnitBase.MAX_SPEED);

            this.angle = Math.toDegrees(Math.atan2(finalSpeed.getY(), finalSpeed.getX()));
            if (angle < 0) {
                angle += 360;
            } else if (angle > 360) {
                angle -= 360;
            }

            this.position = this.position.sumVector(finalSpeed);
            this.adjustPosition();

            this.speed = this.speed.setAngle(angle);

            this.timer = UnitBase.MAX_TIMER;
        } else {
            this.timer -= elapsed;
        }
    }

    @Override
    public final Vector2D getSteeringForce() {
        return new Vector2DImpl(0, 0);
    }

    @Override
    public final Rectangle getArea(final double growFactor) {
        return new Rectangle((int) this.position.getX() - sideLength * AREA / 2,
                (int) this.position.getY() - sideLength * AREA / 2,
                sideLength * AREA,
                sideLength * AREA);
    }

    @Override
    public final Shape getCohesionArea() {
        final Rectangle rectangle = this.getArea(AREA);

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

    @Override
    public final Vector2D getSpeed() {
        return new Vector2DImpl(this.speed);
    }

    @Override
    public final void setSpeed(final Vector2D speed) {
        this.speed = new Vector2DImpl(speed);
    }

    @Override
    public final double getMass() {
        return this.mass;
    }

    @Override
    public final float getTimer() {
        return this.timer;
    }

    @Override
    public final void setTimer(final float timer) {
        this.timer = timer;
    }

    @Override
    public final void adjustPosition() {
        if (this.position.getX() < 0) {
            this.position.setX(ViewImpl.WIDTH);
        }
        if (this.position.getX() > ViewImpl.WIDTH) {
            this.position.setX(0);
        }
        if (this.position.getY() < 0) {
            this.position.setY(ViewImpl.HEIGHT - ViewImpl.TEXT_HEIGHT);
        }
        if (this.position.getY() > ViewImpl.HEIGHT - ViewImpl.TEXT_HEIGHT) {
            this.position.setY(0);
        }
    }
}
