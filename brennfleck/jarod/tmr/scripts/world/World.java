package brennfleck.jarod.tmr.scripts.world;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;

/**
 * Holds all getters and setters for the legitimate Minecraft world instance.
 * 
 * @author Jarod Brennfleck
 */
public class World {
	private static ArrayList<Location> temporaryBlocks = new ArrayList<Location>();
	
	private static WorldClient getWorld() {
		return Minecraft.getMinecraft().theWorld;
	}
	
	/**
	 * Checks whether or not we can interact with the world.
	 */
	public static final boolean isValid() {
		boolean valid = true;
		if(getWorld() == null) valid = false;
		if(!valid) System.out.println("World is bad!");
		return valid;
	}
	
	/**
	 * Returns a block at the coordinates specified.
	 */
	public static Block getBlockAt(int x, int y, int z) {
		return getBlockAt(new Location(x, y, z));
	}
	
	/**
	 * Returns a block at the PreciseLocation, after changing it into a normal
	 * Location.
	 */
	public static Block getBlockAt(PreciseLocation l) {
		Location a = l.getNonPrecise();
		return new Block(net.minecraft.block.Block.getIdFromBlock(getWorld().getBlock((int) a.x, (int) a.y, (int) a.z)));
	}
	
	/**
	 * Returns whether or not the block at the given coordinates is solid.
	 */
	public static boolean isBlockSolid(int x, int y, int z) {
		return isBlockSolid(new Location(x, y, z));
	}
	
	/**
	 * Returns whether or not the block at the given PreciseLocation is solid,
	 * after changing it into a normal Location.
	 */
	public static boolean isBlockSolid(PreciseLocation preciseLocation) {
		Location l = preciseLocation.getNonPrecise();
		return getBlockAt(l).isCollidable();
	}
	
	/**
	 * DEPRECATED - Instead, use:
	 * {@link #getMostSuitableStandingLocation(Location)} <br>
	 * <br>
	 * Get the most solid
	 * {@link brennfleck.jarod.tmr.scripts.world.Location#Location() Location}
	 * underneath the specified location.
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
	 * Returns a location that is solid, and contains two non-solid blocks above
	 * it.
	 */
	public static Location getMostSuitableStandingLocation(Location l) {
		for(int shift = (int) l.y; shift > 0;) {
			Location ret = new Location(l.x, shift, l.z);
			Location tmp = ret.clone();
			if(World.isBlockSolid(ret)) {
				if(!World.isBlockSolid(tmp.shift(0, 1, 0).getNonPrecise()) && !World.isBlockSolid(tmp.shift(0, 1, 0).getNonPrecise())) return ret;
				shift++;
			} else shift--;
		}
		return l;
	}
	
	/**
	 * Sets the location in the world to the block specified. This is only
	 * temporary, however, as soon as the chunk is re-rendered, or out of sight,
	 * this block will remove. This also has no affect on the world. Mining this
	 * block will result in a tool used, but no drop.
	 */
	public static void setTemporaryBlock(Location l, Block b) {
		getWorld().setBlock((int) l.getX(), (int) l.getY(), (int) l.getZ(), net.minecraft.block.Block.getBlockById(b.getID()));
	}
}