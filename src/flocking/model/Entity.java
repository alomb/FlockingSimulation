package flocking.model;

import java.awt.Point;
import java.util.List;

public interface Entity {

	List<Point> getFigure();

	void setFigure(List<Point> figure);

	Point getPosition();

	void setPosition(Point position);

}