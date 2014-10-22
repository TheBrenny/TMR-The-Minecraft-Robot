package brennfleck.jarod.tmr.scripts.events.blocks;

import brennfleck.jarod.tmr.scripts.world.Block;
import brennfleck.jarod.tmr.scripts.world.Location;

public class BlockMetadataChangedEvent extends BlockEvent {
	private int previousMetadata;
	private int newMetadata;
	
	/**
	 * Constructs a new block metadata changed event.
	 */
	public BlockMetadataChangedEvent(Location l, Block b, int previousMetadata, int newMetadata) {
		super(l, b);
		this.previousMetadata = previousMetadata;
		this.newMetadata = newMetadata;
	}
	
	/**
	 * Returns the previous metadata of this block.
	 */
	public int getPreviousMetadata() {
		return previousMetadata;
	}
	
	/**
	 * Returns the new metadata of this block.
	 */
	public int getNewMetadata() {
		return newMetadata;
	}
}