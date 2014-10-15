package brennfleck.jarod.tmr.scripts.world;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;

/**
 * Holds all getters and setters for the legitimate Minecraft world instance.
 * 
 * @author jarod
 */
public class World {
	private static ArrayList<Location> temporaryBlocks = new ArrayList<Location>();
	private static WorldClient getWorld() {
		return Minecraft.getMinecraft().theWorld;
	}
	
	public static final boolean isValid() {
		boolean valid = true;
		if(getWorld() == null) valid = false;
		if(!valid) System.out.println("World is bad!");
		return valid;
	}
	
	public static Block getBlockAt(int x, int y, int z) {
		return getBlockAt(new Location(x, y, z));
	}
	
	public static Block getBlockAt(PreciseLocation l) {
		return new Block(getWorld().getBlock((int) l.x, (int) l.y, (int) l.z));
	}
	
	public static boolean isBlockSolid(int x, int y, int z) {
		return isBlockSolid(new Location(x, y, z));
	}
	
	public static boolean isBlockSolid(Location preciseLocation) {
		return getBlockAt(preciseLocation).isCollidable();
	}
	
	/**
	 * Get the most solid {@link brennfleck.jarod.tmr.scripts.world.Location#Location() Location} underneath the specified location.
	 * 
	 * @author jarod
	 * @useInstead {@link #getMostSuitableStandingLocation(Location)}
	 * @param l The given {@link brennfleck.jarod.tmr.scripts.world.Location#Location() Location}.
	 * @return A {@link brennfleck.jarod.tmr.scripts.world.Location#Location() Location} below or including the given block which is solid.
	 */
	@Deprecated
	public static Location getSolidMostLocationUnder(Location l) {
		for(int shift = (int) l.y; shift > 0; shift--) {
			Location ret = new Location(l.x, shift, l.z);
			if(World.isBlockSolid(ret)) return ret;
		}
		return l;
	}
	
	/**
	 * Gets the most suitable location for a 2 block tall and 1 block wide player.
	 *    
	 * @param l The given {@link brennfleck.jarod.tmr.scripts.world.Location#Location() Location}.
	 * @return A {@link brennfleck.jarod.tmr.scripts.world.Location#Location() Location} which is solid with the 2 consecutive blocks above not being solid. 
	 */
	public static Location getMostSuitableStandingLocation(Location l) {
		for(int shift = (int) l.y; shift > 0;) {
			Location ret = new Location(l.x, shift, l.z);
			Location tmp = ret.clone();
			if(World.isBlockSolid(ret)) {
				if(!World.isBlockSolid(tmp.shift(0,1,0).getNonPrecise()) && !World.isBlockSolid(tmp.shift(0,1,0).getNonPrecise())) return ret;
				shift++;
			} else shift--;
		}
		return l;
	}
	
	public static void setTemporaryBlock(Location l, Block b) {
		getWorld().setBlock((int) l.getX(), (int) l.getY(), (int) l.getZ(), net.minecraft.block.Block.getBlockById(b.getID()));
	}
}