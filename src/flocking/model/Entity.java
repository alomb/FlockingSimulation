package flocking.model;

import java.awt.Point;
import java.util.List;

/**
 * A simulation entity.
 */
public interface Entity {

    /**
     * @return the figure {@link Point}s
     */
    List<Point> getFigure();

    /**
     * @param figure the new figure's {@link Point}s
     */
    void setFigure(List<Point> figure);

    /**
     * @return the central {@link Point} of the entity
     */
    Point getPosition();

    /**
     * @param position the new {@link Entity}'s central {@link Point}
     */
    void setPosition(Point position);

}
