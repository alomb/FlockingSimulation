package flocking.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import flocking.controller.Controller;
import flocking.controller.input.CreateEntityCommand;
import flocking.model.Entity;

/**
 * A generic implementation of {@link Scene}.
 */
public class SceneImpl extends JPanel implements Scene {

    private final Controller controller;

    /**
     * 
     */
    private static final long serialVersionUID = 1521223266538012283L;

    /**
     * @param w the panel width
     * @param h the panel height
     * @param controller the application {@link Controller}
     */
    public SceneImpl(final int w, final int h, final Controller controller) {
        super();
        this.controller = controller;
        this.setSize(w, h);
        this.setBackground(Color.WHITE);
    }

    @Override
    public final void focus() {
        this.addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        requestFocusInWindow();
    }

    @Override
    public final void render() {
        this.repaint();
        //Fixes some OSes bug where graphics scheduling gets slowed down
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    protected final void paintComponent(final Graphics g) {
        super.paintComponent(g);
        this.draw((Graphics2D) g);
    }

    @Override
    public final void draw(final Graphics2D g) {
        for (final Entity e : this.controller.getFigures()) {
            final List<Point> vertices = new ArrayList<>();
            e.getFigure().forEach(f -> vertices.add(new Point((int) Math.round(f.getX()), (int) Math.round(f.getY()))));
            g.draw(e.getCohesionArea());
            this.drawFigure(g, vertices, e.getAngle());
        }
    }

    @Override
    public final void keyTyped(final KeyEvent e) {

    }

    @Override
    public final void keyPressed(final KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.controller.notifyCommand(new CreateEntityCommand());
        }
    }

    @Override
    public final void keyReleased(final KeyEvent e) {

    }

    private void drawFigure(final Graphics2D g, final List<Point> points, final double angle) {
        final GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
                points.size());

        polygon.moveTo(points.get(0).x, points.get(0).y);

        for (int index = 1; index < points.size(); index++) {
                polygon.lineTo(points.get(index).x, points.get(index).y);
        }

        polygon.closePath();

        final AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(angle), getCentroid(points).x, getCentroid(points).y);
        polygon.transform(at);
        g.draw(polygon);
    }

    private Point getCentroid(final List<Point> points) {
        return new Point(points.stream().mapToInt(p -> p.x).sum() / points.size(), 
                points.stream().mapToInt(p -> p.y).sum() / points.size());
    }
}
