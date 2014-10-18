package brennfleck.jarod.tmr.scripts.world;

import net.minecraft.util.MathHelper;

/**
 * A 3 dimensional point in the world which stays precise.
 * 
 * @author Jarod Brennfleck
 */
public class PreciseLocation {
	protected double x;
	protected double y;
	protected double z;
	
	/**
	 * Constructs a Precise Location based of the parameters.
	 */
	public PreciseLocation(double x, double y, double z) {
		place(x, y, z);
	}
	
	/**
	 * Shifts the location to the coordinates and returns itself after.
	 */
	public PreciseLocation shift(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return place(this.x, this.y, this.z);
	}
	
	/**
	 * Places itself at the coordinates specified and returns itself after.
	 */
	public PreciseLocation place(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	/**
	 * Creates a copy of this PreciseLocation, constructing a new
	 * PreciseLocation and setting it's values to this PreciseLocation's values.
	 */
	public PreciseLocation clone() {
		return new PreciseLocation(this.x, this.y, this.z);
	}
	
	/**
	 * Returns a string representation of this object. <br>
	 * EG: "Loc[12,45,23]"
	 */
	public String toString() {
		String name = this instanceof Location ? "Loc" : "PreciseLoc";
		return name + "[" + this.x + "," + this.y + "," + this.z + "]";
	}
	
	/**
	 * Gets this PreciseLocation's x-coordinate, unmodified.
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * Gets this PreciseLocation's y-coordinate, unmodified.
	 */
	public double getY() {
		return this.y;
	}
	
	/**
	 * Gets this PreciseLocation's z-coordinate, unmodified.
	 */
	public double getZ() {
		return this.z;
	}
	
	/**
	 * Returns whether or not the object passed has the same coordinates as this
	 * object.
	 */
	public boolean equals(Object o) {
		if(o instanceof PreciseLocation) {
			PreciseLocation l = (PreciseLocation) o;
			return l.x == this.x && l.y == this.y && l.z == this.z;
		}
		return false;
	}
	
	/**
	 * Creates a new
	 * {@link brennfleck.jarod.tmr.scripts.world.Location#Location() Location}
	 * with the same coordinates as this Precise Location
	 */
	public Location getNonPrecise() {
		return new Location(x, y, z);
	}
}