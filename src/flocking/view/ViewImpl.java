package flocking.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import flocking.model.Model;

/**
 * The implementation of {@link View}.
 */
public class ViewImpl implements View {

    private final JFrame frame;
    private final Scene scene;

    /**
     * Initialize the frame and the window.
     * @param model the application {@link Model}
     */
    public ViewImpl(final Model model) {

        final int h = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        final int w = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();

        this.frame = new JFrame("FlockMovement");
        this.scene = new SceneImpl(w, h, model);
        this.scene.focus();
        frame.getContentPane().add((Component) this.scene);
        frame.setSize(w, h);
        frame.setMinimumSize(new Dimension(w, h));
        frame.setResizable(false);
    }

    @Override
    public final void initialize() {
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent ev) {
                System.exit(0);
            }
            public void windowClosed(final WindowEvent ev) {
                System.exit(0);
            }
        });

        frame.pack();
        frame.setVisible(true);
    }


    @Override
    public final void render() {
        if (this.scene != null) {
            try {
                SwingUtilities.invokeAndWait(() -> {
                    this.scene.render();
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
