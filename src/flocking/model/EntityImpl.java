package flocking.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class EntityImpl implements Entity {

	private Point position;

	//private final int sideLength;
	private List<Point> figure;

	public EntityImpl(final Point startPos, final int sideLength) {
		//this.sideLength = sideLength;
		this.position = startPos;

		this.figure = new ArrayList<>();
		this.figure.add(new Point(startPos.x - sideLength / 2, startPos.y - sideLength / 2));
		this.figure.add(new Point(startPos.x + sideLength / 2, startPos.y - sideLength / 2));
		this.figure.add(new Point(startPos.x, startPos.y + sideLength / 2));
	}

	@Override
	public final List<Point> getFigure() {
		return this.figure;
	}

	@Override
	public final void setFigure(final List<Point> figure) {
		this.figure = figure;
	}

	@Override
	public final Point getPosition() {
		return this.position;
	}

	@Override
	public final void setPosition(final Point position) {
		this.position = position;
	}
}
