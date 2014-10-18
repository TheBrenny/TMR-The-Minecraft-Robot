package brennfleck.jarod.tmr.scripts.world;

/**
 * Describes a 3D area.
 * 
 * @author Jarod Brennfleck
 */
public class Area {
	public double minX, minY, minZ;
	public double maxX, maxY, maxZ;
	
	/**
	 * Constructs a precise area.
	 */
	public Area(PreciseLocation loc1, PreciseLocation loc2) {
		this(loc1.x, loc1.y, loc1.z, loc2.x, loc2.y, loc2.z);
	}
	
	/**
	 * Constructs a non-precise area.
	 */
	public Area(Location loc1, Location loc2) {
		this(loc1.x, loc1.y, loc1.z, loc2.x, loc2.y, loc2.z);
	}
	
	/**
	 * Constructs an area using the given parameters.
	 */
	public Area(double x, double y, double z, double x2, double y2, double z2) {
		double tmp;
		if(x2 < x) {
			tmp = x2;
			x2 = x;
			x = tmp;
		}
		if(y2 < y) {
			tmp = y2;
			y2 = y;
			y = tmp;
		}
		if(z2 < z) {
			tmp = z2;
			z2 = z;
			z = tmp;
		}
		this.minX = x;
		this.minY = y;
		this.minZ = z;
		this.maxX = x2;
		this.maxY = y2;
		this.maxZ = z2;
	}
	
	/**
	 * Returns whether or not this area contains the location.
	 */
	public boolean contains(PreciseLocation l) {
		if(l.x < minX || l.y < minY || l.z < minZ) return false;
		if(l.x > maxX || l.y > maxY || l.z > maxZ) return false;
		return true;
	}
}