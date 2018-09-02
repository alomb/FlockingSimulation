package flocking.model.unit;

import java.awt.Rectangle;
import java.awt.Shape;
import java.util.List;

import flocking.model.Vector2D;

/**
 * The decorator base class.
 */
public abstract class UnitDecorator implements Unit {

    private final Unit unit;

    /**
     * @param unit the {@link Unit} to decorate
     */
    public UnitDecorator(final Unit unit) {
        this.unit = unit;
    }

    @Override
    public final List<Vector2D> getFigure() {
        return this.unit.getFigure();
    }

    @Override
    public final void setFigure(final List<Vector2D> figure) {
        this.unit.setFigure(figure);
    }

    @Override
    public final Vector2D getPosition() {
        return this.unit.getPosition();
    }

    @Override
    public final void setPosition(final Vector2D position) {
        this.unit.setPosition(position);
    }

    @Override
    public final void update(final float elapsed) {
        if (this.unit.getTimer() <= 0) {
            Vector2D result = this.getSteeringForce();
            result = result.clampMagnitude(UnitImpl.MAX_FORCE);
            result = result.mulScalar(1 / this.unit.getMass());

            final Vector2D finalSpeed = this.getSpeed().sumVector(result).clampMagnitude(UnitImpl.MAX_SPEED);

            this.unit.setAngle(Math.toDegrees(Math.atan2(finalSpeed.getY(), finalSpeed.getX())));
            if (this.unit.getAngle() < 0) {
                this.unit.setAngle(this.getAngle() + 360);
            } else if (this.unit.getAngle() > 360) {
                this.unit.setAngle(this.getAngle() - 360);
            }

            this.setPosition(this.getPosition().sumVector(finalSpeed));
            this.unit.adjustPosition();

            this.unit.setSpeed(this.getSpeed().setAngle(this.getAngle()));

            this.unit.setTimer(UnitImpl.MAX_TIMER);
        } else {
            this.setTimer(this.unit.getTimer() - elapsed);
        }
    }

    @Override
    public final Rectangle getArea(final double growFactor) {
        return this.unit.getArea(growFactor);
    }

    @Override
    public final int getSideLength() {
        return this.unit.getSideLength();
    }

    @Override
    public final Shape getCohesionArea() {
        return this.unit.getCohesionArea();
    }

    @Override
    public final Vector2D getSpeed() {
        return this.unit.getSpeed();
    }

    @Override
    public final void setSpeed(final Vector2D speed) {
        this.unit.setSpeed(speed);
    }

    @Override
    public final double getAngle() {
        return this.unit.getAngle();
    }

    @Override
    public final void setAngle(final double degrees) {
        this.unit.setAngle(degrees);
    }

    @Override
    public abstract Vector2D getSteeringForce();

    @Override
    public final double getMass() {
        return this.unit.getMass();
    }

    @Override
    public final float getTimer() {
        return this.unit.getTimer();
    }

    @Override
    public final void setTimer(final float timer) {
        this.unit.setTimer(timer);
    }

    @Override
    public final void adjustPosition() {
        this.unit.adjustPosition();
    }
}
