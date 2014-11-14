package brennfleck.jarod.tmr.scripts.entities;

import net.minecraft.entity.EntityList;
import net.minecraft.util.StatCollector;
import brennfleck.jarod.helpfulthings.BrennyHelpful.MathHelpful;
import brennfleck.jarod.tmr.scripts.world.Location;
import brennfleck.jarod.tmr.scripts.world.PreciseLocation;

/**
 * A class of which entities are accessed through. This is an entity of all
 * types.
 * 
 * @author Jarod Brennfleck
 */
public class Entity {
	public static final String MAIN = "eye";
	public static final String BASE = "feet";
	protected net.minecraft.entity.Entity theRealEntity;
	
	public Entity(net.minecraft.entity.Entity e) {
		this.theRealEntity = e;
	}
	
	/**
	 * Returns the location of the entity, with the y-coordinate depending on
	 * the <code>spot</code>, rounded to become a
	 * {@link brennfleck.jarod.tmr.scripts.world.Location#Location Location}.
	 * 
	 * @see {@link #MAIN}
	 * @see {@link #BASE}
	 */
	public Location getLocation(String spot) {
		return getPreciseLocation(spot).getNonPrecise();
	}
	
	/**
	 * Returns the location of the entity, with the y-coordinate depending on
	 * the <code>spot</code>, as a
	 * {@link brennfleck.jarod.tmr.scripts.world.PreciseLocation#PreciseLocation
	 * PreciseLocation}.
	 * 
	 * @see {@link #MAIN}
	 * @see {@link #BASE}
	 */
	public PreciseLocation getPreciseLocation(String spot) {
		double theY = spot.equalsIgnoreCase(MAIN) ? theRealEntity.posY : theRealEntity.boundingBox.minY;
		return new PreciseLocation(theRealEntity.posX, theY, theRealEntity.posZ);
	}
	
	/**
	 * Returns the squared distance to a precise location.
	 */
	public double getDistanceSqToPreciseLocation(String whereFrom, PreciseLocation l) {
		PreciseLocation pl = getPreciseLocation(whereFrom);
		double var2 = pl.getX() - l.getX();
		double var4 = pl.getY() - l.getY();
		double var6 = pl.getZ() - l.getZ();
		return var2 * var2 + var4 * var4 + var6 * var6;
	}
	
	/**
	 * Returns the exact distance to the specified entity
	 */
	public double getDistanceToPreciseLocation(String whereFrom, PreciseLocation l) {
		PreciseLocation pl = getPreciseLocation(whereFrom);
		double width = pl.getX() - l.getX();
		double height = pl.getY() - l.getY();
		double length = pl.getZ() - l.getZ();
		double newL = MathHelpful.sqrt_double(width * width + length * length);
		return MathHelpful.sqrt_double(newL * newL + height * height);
	}
	
	/**
	 * Returns the name of this entity.
	 */
	public String getName() {
		return theRealEntity.getCommandSenderName();
	}
	
	public net.minecraft.entity.Entity getRealEntity() {
		return theRealEntity;
	}
}