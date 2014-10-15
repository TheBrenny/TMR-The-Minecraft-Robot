package brennfleck.jarod.helpfulthings;

import java.awt.geom.Rectangle2D;

public class BrennyRectangle extends Rectangle2D.Double {
	public BrennyRectangle(double x, double y, double width, double height) {
		super(x, y, width, height);
	}
	public boolean contains(BrennyPoint point) {
		return point.x > this.x && point.x < this.x + width && point.y > this.y && point.y < this.y + height;
	}
	public BrennyRectangle move(BrennyPoint point) {
		this.x = point.x;
		this.y = point.y;
		return this;
	}
	public BrennyRectangle shift(double x, double y) {
		this.x += x;
		this.y += y;
		return this;
	}
	public BrennyRectangle resize(double width, double height) {
		this.width = width;
		this.height = height;
		return this;
	}
}