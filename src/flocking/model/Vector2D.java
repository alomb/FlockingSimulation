package flocking.model;

import javax.naming.OperationNotSupportedException;

/**
 * A bidimensional vector.
 */
public interface Vector2D {

    /**
     * @return the horizontal coordinate
     */
    double getX();

    /**
     * @param x the new horizontal coordinate
     */
    void setX(double x);

    /**
     * @return the vertical coordinate
     */
    double getY();

    /**
     * @param y the new vertical coordinate
     */
    void setY(double y);

    /**
     * @return the vector length
     */
    double length();

    /**
     * @return the vector normalized
     * @throws OperationNotSupportedException 
     */
    Vector2D normalize() throws OperationNotSupportedException;

    /**
     * @param dx the horizontal offset to add
     * @param dy the vertical offset to add
     * @return the result
     */
    Vector2D translate(double dx, double dy);

    /**
     * @param scalar to multiply
     * @return the result
     */
    Vector2D mulScalar(double scalar);

    /**
     * @param vector to sum
     * @return the result
     */
    Vector2D sumVector(Vector2D vector);

    /**
     * @param deltaAngle the rotation angle in degrees
     * @return the vector rotated
     */
    Vector2D rotate(double deltaAngle);

    /**
     * @param angle the angle the vector has with its origin 
     * @return the vector rotated
     */
    Vector2D setAngle(double angle);

    /**
     * @param maxLength the vector's max magnitude
     * @return the clamped vector
     */
    Vector2D clampMagnitude(double maxLength);

    /**
     * @param v the other vector
     * @return the distance beetween them
     */
    double distance(Vector2D v);
}
