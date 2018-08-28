package flocking.model;

import java.awt.Rectangle;
import java.util.List;

/**
 * A simulation entity.
 */
public interface Entity {

    /**
     * @return the figure {@link Point}s
     */
    List<Vector2D> getFigure();

    /**
     * @param figure the new figure's {@link Point}s
     */
    void setFigure(List<Vector2D> figure);

    /**
     * @return the central {@link Point} of the entity
     */
    Vector2D getPosition();

    /**
     * @param position the new {@link Entity}'s central {@link Point}
     */
    void setPosition(Vector2D position);

    /**
     * @param elapsed the time elapsed in the loop cycle
     */
    void update(float elapsed);

    /**
     * @param growFactor a factor to resize the area
     * @return the unit occupied area multiplied by the factor
     */
    Rectangle getArea(double growFactor);

}
