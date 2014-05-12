package polyominos;

import java.awt.Point;

public class PolyominoPattern {
	private Point[] coords;
	private int color;
	
	public PolyominoPattern(Point[] coords, int color) {
		this.setCoords(coords);
		this.setColor(color);
	}

	public Point[] getCoords() {
		return coords;
	}

	public void setCoords(Point[] coords) {
		this.coords = coords;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

}
