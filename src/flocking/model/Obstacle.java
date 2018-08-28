package flocking.model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * An unmovable {@link Entity} that {@link Unit}s must avoid.
 */
public class Obstacle implements Entity {

    private Vector2D position;
    private final List<Vector2D> figure;

    private final int width;
    private final int height;

    private static final int SIZE_BOUND = 30;


    /**
     * @param startPos the first position
     */
    public Obstacle(final Vector2D startPos) {
        this.position = new Vector2DImpl(startPos);
        this.figure = new ArrayList<>();
        this.width = new Random().nextInt(Obstacle.SIZE_BOUND) + SIZE_BOUND / 2;
        this.height = new Random().nextInt(Obstacle.SIZE_BOUND) + SIZE_BOUND / 2;
        this.figure.add(new Vector2DImpl(this.position.getX() + this.getArea(1).x, 
                this.position.getY() + this.getArea(1).y));
        this.figure.add(new Vector2DImpl(this.position.getX() + this.getArea(1).x + this.width, 
                this.position.getY() + this.getArea(1).y));
        this.figure.add(new Vector2DImpl(this.position.getX() + this.getArea(1).x + this.width, 
                this.position.getY() + this.getArea(1).y + this.height));
        this.figure.add(new Vector2DImpl(this.position.getX() + this.getArea(1).x, 
                this.position.getY() + this.getArea(1).y + this.height));
    }

    @Override
    public final List<Vector2D> getFigure() {
        System.out.println(figure);
        return Collections.unmodifiableList(this.figure);
    }

    @Override
    public final void setFigure(final List<Vector2D> figure) {
        Collections.copy(figure, this.figure);
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
    public final void update(final float elapsed) {
 
    }

    @Override
    public final Rectangle getArea(final double growFactor) {
        return new Rectangle(this.width, this.height);
    }

}
