package flocking.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import flocking.controller.Controller;

/**
 * The implementation of {@link View}.
 */
public class ViewImpl implements View {

    private final JFrame frame;
    private Scene scene;

    private final int width;
    private final int height;

    /**
     * Initialize the frame and the window.
     */
    public ViewImpl() {

        this.height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        this.frame = new JFrame("FlockMovement");
        frame.setSize(this.width, this.height);
        frame.setMinimumSize(new Dimension(this.width, this.height));
        frame.setResizable(false);
    }

    @Override
    public final void initialize(final Controller controller) {
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent ev) {
                System.exit(0);
            }
            public void windowClosed(final WindowEvent ev) {
                System.exit(0);
            }
        });

        this.scene = new SceneImpl(this.width, this.height, controller);
        frame.getContentPane().add((Component) this.scene);
        this.scene.focus();

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
