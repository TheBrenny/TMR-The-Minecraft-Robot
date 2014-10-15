package brennfleck.jarod.tmr.scripts.world;

import net.minecraft.util.MathHelper;

public class PreciseLocation {
	protected double x;
	protected double y;
	protected double z;
	
	public PreciseLocation(double x, double y, double z) {
		place(x, y, z);
	}
	
	public PreciseLocation shift(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return place(this.x, this.y, this.z);
	}
	
	public PreciseLocation place(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	public PreciseLocation clone() {
		return new PreciseLocation(this.x, this.y, this.z);
	}
	
	public String toString() {
		String name = this instanceof Location ? "Loc" : "PreciseLoc";
		return name + "[" + this.x + "," + this.y + "," + this.z + "]";
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public double getZ() {
		return this.z;
	}
	
	public boolean equals(Object o) {
		if(o instanceof PreciseLocation) {
			PreciseLocation l = (PreciseLocation) o;
			return l.x == this.x && l.y == this.y && l.z == this.z;
		}
		return false;
	}
	
	public Location getNonPrecise() {
		return new Location(x, y, z);
	}
}