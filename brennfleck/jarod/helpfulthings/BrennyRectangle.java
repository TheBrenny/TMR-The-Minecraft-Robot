package brennfleck.jarod.helpfulthings;

import java.awt.geom.Rectangle2D;

/**
 * A class that stores data for a Rectangle.
 * 
 * @author Jarod Brennfleck
 */
public class BrennyRectangle extends Rectangle2D.Double {
	/**
	 * Constructs a rectangle given the x, y, width and height.
	 */
	public BrennyRectangle(double x, double y, double width, double height) {
		super(x, y, width, height);
	}
	
	/**
	 * Returns whether or not that this rectangle contains a point.
	 */
	public boolean contains(BrennyPoint point) {
		return point.x > this.x && point.x < this.x + width && point.y > this.y && point.y < this.y + height;
	}
	
	/**
	 * Moves this rectangle to the specified point.
	 */
	public BrennyRectangle move(BrennyPoint point) {
		this.x = point.x;
		this.y = point.y;
		return this;
	}
	
	/**
	 * Shifts this rectangle, adding the x to this x, and the y to this y.
	 */
	public BrennyRectangle shift(double x, double y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	/**
	 * Resizes this rectangle to the specified width and height.
	 */
	public BrennyRectangle resize(double width, double height) {
		this.width = width;
		this.height = height;
		return this;
	}
}