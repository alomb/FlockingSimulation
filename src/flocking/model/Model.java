package flocking.model;

import java.util.List;

public interface Model {

	void update(float elapsed);

	List<Entity> getFigures();

	void recalculate();

}
