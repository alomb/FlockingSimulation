package flocking.model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import flocking.view.ViewImpl;

/**
 * An entity implementation of an object who change position randomly at a fixed time.
 */
public class Target implements Entity {

    private static final int MAX_TIMER = 5000;
    private int currentTimer;

    private Vector2D position;
    private static final int SIZE = 30;

    /**
     * Initialize the object.
     */
    public Target() {
        this.currentTimer = MAX_TIMER;
        this.getRandomPosition();
    }

    @Override
    public final List<Vector2D> getFigure() {
        return new ArrayList<>();
    }

    @Override
    public final void setFigure(final List<Vector2D> figure) {

    }

    @Override
    public final Vector2D getPosition() {
        return new Vector2DImpl(this.position);
    }

    @Override
    public final void setPosition(final Vector2D position) {
        this.position = new Vector2DImpl(position);
    }

    @Override
    public final void update(final float elapsed) {
        if (this.currentTimer >= Target.MAX_TIMER) {
            this.currentTimer = 0;
            this.getRandomPosition();
        } else {
            this.currentTimer += elapsed;
        }
    }

    @Override
    public final Rectangle getArea(final double growFactor) {
        return new Rectangle((int) Math.round(this.position.getX()),
                (int) Math.round(this.position.getY()), 
                Target.SIZE,
                Target.SIZE);
    }

    private void getRandomPosition() {
        final Random rnd = new Random();
        this.position = new Vector2DImpl(rnd.nextInt(ViewImpl.WIDTH),
                rnd.nextInt(ViewImpl.HEIGHT - ViewImpl.TEXT_HEIGHT));
    }

}
