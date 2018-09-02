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
import flocking.model.Entity;
import flocking.model.Target;
import flocking.model.unit.Unit;

/**
 * A generic implementation of {@link Scene} for the simulation.
 */
public class SimulationScene extends JPanel implements Scene {

    private final Controller controller;

    private boolean toogleGizmos;
    private final Color obstacleColor = new Color(5, 205, 50, 150);
    private final Color unitColor = new Color(5, 205, 50, 255);
    private final Color targetColor = new Color(205, 92, 92, 150);


    /**
     * 
     */
    private static final long serialVersionUID = 1521223266538012283L;

    /**
     * @param w the panel width
     * @param h the panel height
     * @param controller the application {@link Controller}
     */
    public SimulationScene(final int w, final int h, final Controller controller) {
        super();
        this.controller = controller;
        this.setBounds(0, 0, w, h);
        this.setBackground(Color.BLACK);
        this.addKeyListener(this);
    }

    @Override
    public final void focus() {
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
            if (e instanceof Unit) {
                if (this.toogleGizmos) {
                    g.setColor(Color.GRAY);
                    g.draw(((Unit) e).getCohesionArea());
                    //g.draw(((Unit) e).getLine());
                }
                g.setColor(this.unitColor);
                this.drawFigure(g, vertices, ((Unit) e).getAngle());
            } else if (e instanceof Target) {
                g.setColor(this.targetColor);
                g.fillRect(e.getArea(1).x, e.getArea(1).y, e.getArea(1).width, e.getArea(1).height);
            } else {
                g.setColor(this.obstacleColor);
                g.fillRect(e.getArea(1).x, e.getArea(1).y, e.getArea(1).width, e.getArea(1).height);
            }
        }
    }

    @Override
    public final void keyTyped(final KeyEvent e) {
        if (e.getKeyChar() == 't') {
            this.toogleGizmos = !this.toogleGizmos;
        } else if (e.getKeyChar() == 'p') {
            this.controller.pause();
        }
    }

    @Override
    public final void keyPressed(final KeyEvent e) {

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
