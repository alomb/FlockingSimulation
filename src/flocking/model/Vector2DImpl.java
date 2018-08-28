package flocking.model;

/**
 * Implementation of {@link Vector2D}.
 */
public class Vector2DImpl implements Vector2D {

    private double x;
    private double y;

    /**
     * @param x the horizontal coordinate
     * @param y the vertical coordinate
     */
    public Vector2DImpl(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param vector a {@link Vector2D} to copy
     */
    public Vector2DImpl(final Vector2D vector) {
        this.x = vector.getX();
        this.y = vector.getY();
    }

    @Override
    public final double getX() {
        return x;
    }

    @Override
    public final void setX(final double x) {
        this.x = x;
    }

    @Override
    public final double getY() {
        return y;
    }

    @Override
    public final void setY(final double y) {
        this.y = y;
    }

    @Override
    public final double lenght() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    @Override
    public final Vector2D normalize() {
        return new Vector2DImpl(this.x / this.lenght(), this.y / this.lenght());
    }

    @Override
    public final Vector2D translate(final double dx, final double dy) {
        return new Vector2DImpl(this.x + dx, this.y + dy);
    }

    @Override
    public final Vector2D mulScalar(final double scalar) {
        return new Vector2DImpl(this.x * scalar, this.y * scalar);
    }

    @Override
    public final Vector2D sumVector(final Vector2D vector) {
        return new Vector2DImpl(this.x + vector.getX(), this.y + vector.getY());
    }

    @Override
    public final Vector2D rotate(final double deltaAngle) {
        final Vector2D result = new Vector2DImpl(this.x, this.y);
        final double deltaAngleRad = Math.toRadians(deltaAngle);

        result.setX(this.x * Math.cos(deltaAngleRad) - this.y * Math.sin(deltaAngleRad));
        result.setY(this.x * Math.sin(deltaAngleRad) + this.y * Math.cos(deltaAngleRad));
        return result;
    }

    @Override
    public final Vector2D setAngle(final double angle) {
        final double length = this.lenght();

        return new Vector2DImpl(Math.cos(Math.toRadians(angle)) * length, 
                Math.sin(Math.toRadians(angle)) * length);
    }

    @Override
    public final Vector2D clampMagnitude(final double maxLength) {
        if (this.lenght() > maxLength) {
            return new Vector2DImpl(this.x * maxLength / this.lenght(),
                    this.y * maxLength / this.lenght());
        }

        return this;
    }

    @Override
    public final String toString() {
        return new String("(" + this.x + ", " + this.y + ")");
    }
}
