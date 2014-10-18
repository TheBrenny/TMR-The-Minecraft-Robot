package brennfleck.jarod.tmr.scripts.world;

import brennfleck.jarod.helpfulthings.BrennyHelpful;
import net.minecraft.util.MathHelper;

/**
 * A 3 dimensional point in the world which rounds down to an integer.
 * 
 * @author Jarod Brennfleck
 */
public class Location extends PreciseLocation {
	/**
	 * Constructs a Location using the parameters.
	 */
	public Location(double x, double y, double z) {
		super(x, y, z);
		place(x, y, z);
	}
	
	/**
	 * Gets the
	 * {@link brennfleck.jarod.helpfulthings.BrennyHelpful.MathHelpful#floor_double(double)
	 * floor} of this Location's x-coordinate.
	 */
	public double getX() {
		return BrennyHelpful.MathHelpful.floor_double(this.x);
	}
	
	/**
	 * Gets the
	 * {@link brennfleck.jarod.helpfulthings.BrennyHelpful.MathHelpful#floor_double(double)
	 * floor} of this Location's y-coordinate.
	 */
	public double getY() {
		return BrennyHelpful.MathHelpful.floor_double(this.y);
	}
	
	/**
	 * Gets the
	 * {@link brennfleck.jarod.helpfulthings.BrennyHelpful.MathHelpful#floor_double(double)
	 * floor} of this Location's z-coordinate.
	 */
	public double getZ() {
		return BrennyHelpful.MathHelpful.floor_double(this.z);
	}
	
	/**
	 * Moves this location to the coordinates specified.
	 */
	public Location place(double x, double y, double z) {
		x = BrennyHelpful.MathHelpful.floor_double(x);
		y = BrennyHelpful.MathHelpful.floor_double(y);
		z = MathHelper.floor_double(z);
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	/**
	 * Creates a clone of this Location, returning a new Location with the same
	 * values.
	 */
	public Location clone() {
		return new Location(this.x, this.y, this.z);
	}
	
	/**
	 * Returns whether or not the object passed has the same coordinates as this
	 * object.
	 */
	public boolean equals(Object o) {
		if(o instanceof Location) {
			Location l = (Location) o;
			return l.x == this.x && l.y == this.y && l.z == this.z;
		}
		return false;
	}
}