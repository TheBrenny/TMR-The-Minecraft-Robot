package brennfleck.jarod.tmr.scripts.entities;

import brennfleck.jarod.tmr.scripts.world.Location;
import brennfleck.jarod.tmr.scripts.world.PreciseLocation;

public class Entity {
	public static final String MAIN = "eye";
	public static final String BASE = "feet";
	protected net.minecraft.entity.Entity theRealEntity;
	
	public Entity(net.minecraft.entity.Entity e) {}
	
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
}