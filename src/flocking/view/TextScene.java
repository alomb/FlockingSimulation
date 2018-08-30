package flocking.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import flocking.controller.Controller;
import flocking.controller.input.CommandImpl;

/**
 * An implementation of {@link Scene} used to insert commands.
 */
public class TextScene extends JPanel implements Scene {

    /**
     * 
     */
    private static final long serialVersionUID = -5995731189455386061L;
    private String commands = "r";

    private final Controller controller;

    /**
     * @param w the panel width
     * @param h the panel height
     * @param controller the application {@link Controller}
     */
    public TextScene(final int w, final int h, final Controller controller) {
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
    public final void keyTyped(final KeyEvent e) {
        if (Character.isLetter(e.getKeyChar()) || Character.isDigit(e.getKeyChar())) {
            this.commands = this.commands + e.getKeyChar();
        }
    }

    @Override
    public final void keyPressed(final KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            this.commands = this.commands.length() > 0
                    ? this.commands.substring(0, this.commands.length() - 1)
                            : this.commands;
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.controller.notifyCommand(new CommandImpl(this.commands.trim()));
            this.commands = "";
        }
    }

    @Override
    public final void keyReleased(final KeyEvent e) {

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
        g.setColor(Color.WHITE);
        g.drawString(">>   " + this.commands, 0, 10 * 2);
    }
}
