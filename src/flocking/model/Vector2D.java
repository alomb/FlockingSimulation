package flocking.model;

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
    double lenght();

    /**
     * @return the vector normalized
     */
    Vector2D normalize();

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

}
