package brennfleck.jarod.helpfulthings;

import java.awt.geom.Point2D;

/**
 * A class that stores data for a point.
 * 
 * @author Jarod Brennfleck
 */
public class BrennyPoint extends Point2D.Double {
	/**
	 * Constructs a point at 0,0.
	 */
	public BrennyPoint() {
		super(0, 0);
	}
	
	/**
	 * Constructs a point at x,y
	 */
	public BrennyPoint(double x, double y) {
		super(x, y);
	}
	
	/**
	 * Sets this locations x-coordinate.
	 */
	public void setLocationX(double x) {
		this.x = x;
	}
	
	/**
	 * Sets this locations y-coordinate.
	 */
	public void setLocationY(double y) {
		this.y = y;
	}
	
	/**
	 * Makes a rectangle using the range with this point being the midpoint.
	 * 
	 * @see {@link #makeRectangle(double, double)}
	 */
	public BrennyRectangle makeRectangle(double range) {
		return makeRectangle(range, range);
	}
	
	/**
	 * Makes a rectangle using the width and height with this point being the
	 * midpoint.
	 * 
	 * @see {@link #makeRectangle(double, double)}
	 */
	public BrennyRectangle makeRectangle(double width, double height) {
		return new BrennyRectangle(this.x - width, this.y - height, width * 2, height * 2);
	}
}