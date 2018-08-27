package flocking.model;

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
     * @return the entity's rotation angle
     */
    double getAngle();

    /**
     * @param position the new {@link Entity}'s central {@link Point}
     */
    void setPosition(Vector2D position);

    /**
     * @param elapsed the time elapsed in the loop cycle
     */
    void update(float elapsed);

}
