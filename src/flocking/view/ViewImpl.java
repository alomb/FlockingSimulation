package flocking.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import flocking.controller.Controller;

/**
 * The implementation of {@link View}.
 */
public class ViewImpl implements View {

    private final JFrame frame;
    private Scene scene;
    private Scene textPanel;
    private final JSplitPane splitPane;

    public static final int WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 10);
    public static final int HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 10);
    /**
     * The text panel y position coordinate.
     */
    public static final int TEXT_HEIGHT = ViewImpl.HEIGHT / 10;

    /**
     * Initialize the frame and the window.
     */
    public ViewImpl() {
        this.frame = new JFrame("FlockMovement");
        this.frame.setSize(ViewImpl.WIDTH, ViewImpl.HEIGHT);
        this.frame.setMinimumSize(new Dimension(ViewImpl.WIDTH, ViewImpl.HEIGHT));
        this.frame.setResizable(false);
        this.frame.getContentPane().setLayout(new GridLayout());

        this.splitPane = new JSplitPane();
        this.frame.getContentPane().add(this.splitPane);
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

        this.scene = new SimulationScene(ViewImpl.WIDTH, ViewImpl.HEIGHT - ViewImpl.TEXT_HEIGHT, controller);
        ((Component) this.scene).addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                super.mouseReleased(e);
                ViewImpl.this.scene.focus();
            }
        });

        this.textPanel = new TextScene(ViewImpl.WIDTH, ViewImpl.TEXT_HEIGHT, controller);
        ((Component) this.textPanel).addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                super.mouseReleased(e);
                ViewImpl.this.textPanel.focus();
            }
        });

        this.splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        this.splitPane.setDividerLocation(ViewImpl.HEIGHT - ViewImpl.TEXT_HEIGHT);
        this.splitPane.setTopComponent((Component) this.scene);
        this.splitPane.setBottomComponent((Component) this.textPanel);
        this.splitPane.setEnabled(false);

        this.textPanel.focus();
        this.frame.pack();
        this.frame.setVisible(true);

    }


    @Override
    public final void render() {
        if (this.scene != null && this.textPanel != null) {
            try {
                SwingUtilities.invokeAndWait(() -> {
                    this.scene.render();
                    this.textPanel.render();
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
