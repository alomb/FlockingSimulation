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

    /**
     * Create an obstacle of random size.
     * @param startPos the first position
     * @param width the obstacle's width
     * @param heigth the obstacle's height
     */
    public Obstacle(final Vector2D startPos, final int width, final int heigth) {
        this.position = new Vector2DImpl(startPos);
        this.figure = new ArrayList<>();
        this.width = width;
        this.height = heigth;
        this.figure.add(new Vector2DImpl(this.position.getX() - this.width / 2, 
                this.position.getY() - this.height / 2));
        this.figure.add(new Vector2DImpl(this.position.getX() + this.width / 2, 
                this.position.getY() - this.height / 2));
        this.figure.add(new Vector2DImpl(this.position.getX() + this.width / 2, 
                this.position.getY() + this.height / 2));
        this.figure.add(new Vector2DImpl(this.position.getX() - this.width / 2, 
                this.position.getY() + this.height / 2));
    }

    /**
     * Create an obstacle of random size.
     * @param startPos the first position
     * @param maxSize the width and height max size (maxSize + maxSize / 2) and min size (maxSize / 2)
     */
    public Obstacle(final Vector2D startPos, final int maxSize) {
        this(startPos, 
                new Random().nextInt(maxSize) + maxSize / 2, 
                new Random().nextInt(maxSize) + maxSize / 2);
    }

    @Override
    public final List<Vector2D> getFigure() {
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
        return new Rectangle((int) Math.round(this.position.getX() - this.width / 2), 
                (int) Math.round(this.position.getY() - this.height / 2), 
                this.width, 
                this.height);
    }

}
