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

    private final int width;
    private final int height;
    /**
     * The text panel y position choordinate.
     */
    public static final int TEXT_HEIGHT = 75;

    /**
     * Initialize the frame and the window.
     */
    public ViewImpl() {

        this.height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        this.frame = new JFrame("FlockMovement");
        this.frame.setSize(this.width, this.height);
        this.frame.setMinimumSize(new Dimension(this.width, this.height));
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

        this.scene = new SimulationScene(this.width, this.height - ViewImpl.TEXT_HEIGHT, controller);
        ((Component) this.scene).addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                super.mouseReleased(e);
                ViewImpl.this.scene.focus();
            }
        });

        this.textPanel = new TextScene(this.width, ViewImpl.TEXT_HEIGHT, controller);
        ((Component) this.textPanel).addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                super.mouseReleased(e);
                ViewImpl.this.textPanel.focus();
            }
        });

        this.splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        this.splitPane.setDividerLocation(this.height - ViewImpl.TEXT_HEIGHT);
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
