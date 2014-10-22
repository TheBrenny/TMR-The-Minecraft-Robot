package brennfleck.jarod.tmr.scripts.events.blocks;

import net.minecraft.entity.player.EntityPlayer;
import brennfleck.jarod.tmr.scripts.world.Block;
import brennfleck.jarod.tmr.scripts.world.Location;

public class BlockChangedEvent extends BlockEvent {
	private Block previousBlock;
	
	public BlockChangedEvent(Location l, Block previousBlock, Block newBlock) {
		super(l, newBlock);
		this.previousBlock = previousBlock;
	}
	
	public Block getPreviousBlock() {
		return previousBlock;
	}
}