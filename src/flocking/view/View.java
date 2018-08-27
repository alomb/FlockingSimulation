package flocking.view;

/**
 * The responsible of rendering and scene managing.
 */
public interface View {

	/**
	 * Initialize the view.
	 */
	void initialize();
	
	/**
     * Render the current {@link Scene}.
     */
    void render();

}
