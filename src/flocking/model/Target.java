package flocking.model;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        this.position = new Vector2DImpl(0, 0);
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
            final Random rnd = new Random();
            this.position = new Vector2DImpl(rnd.nextInt((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()),
                    rnd.nextInt((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
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

}
