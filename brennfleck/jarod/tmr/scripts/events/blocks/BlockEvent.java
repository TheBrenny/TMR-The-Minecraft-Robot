package brennfleck.jarod.tmr.scripts.events.blocks;

import brennfleck.jarod.tmr.scripts.world.Block;
import brennfleck.jarod.tmr.scripts.world.Location;

/**
 * A Block Placed Event is an event that occurs whenever the client receives an
 * update of a block being placed in the world.
 * 
 * @author Jarod Brennfleck
 */
public abstract class BlockEvent {
	private Location location;
	private Block block;
	
	/**
	 * Constructs a new block placed event.
	 */
	public BlockEvent(Location l, Block b) {
		this.location = l;
		this.block = b;
	}
	
	/**
	 * Returns the location of the culprit block.
	 */
	public Location getLocation() {
		return location;
	}
	
	/**
	 * Returns the culprit block.
	 */
	public Block getBlock() {
		return block;
	}
}