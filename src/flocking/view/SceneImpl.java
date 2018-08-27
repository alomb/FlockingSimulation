package flocking.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.geom.GeneralPath;
import java.util.List;

import javax.swing.JPanel;

import flocking.model.Model;

/**
 * A generic implementation of {@link Scene}.
 */
public class SceneImpl extends JPanel implements Scene {

    private final Model model;

    /**
     * 
     */
    private static final long serialVersionUID = 1521223266538012283L;

    /**
     * @param w the panel width
     * @param h the panel height
     * @param model the application {@link Model}
     */
    public SceneImpl(final int w, final int h, final Model model) {
        super();
        this.model = model;
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
        this.draw(g);
    }

    @Override
    public final void draw(final Graphics g) {
        this.model.getFigures().forEach(f -> {
            this.drawFigure(g, f.getFigure());
        });
    }

    @Override
    public final void keyTyped(final KeyEvent e) {

    }

    @Override
    public final void keyPressed(final KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.model.recalculate();
            System.out.println("sds");
        }
    }

    @Override
    public final void keyReleased(final KeyEvent e) {

    }

    private void drawFigure(final Graphics g, final List<Point> points) {
        final GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
                points.size());

        polygon.moveTo(points.get(0).x, points.get(0).y);

        for (int index = 1; index < points.size(); index++) {
                polygon.lineTo(points.get(index).x, points.get(index).y);
        }

        polygon.closePath();
        ((Graphics2D) g).draw(polygon);
    }
}
