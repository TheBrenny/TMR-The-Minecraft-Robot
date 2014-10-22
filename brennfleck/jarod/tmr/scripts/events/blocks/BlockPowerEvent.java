package brennfleck.jarod.tmr.scripts.events.blocks;

import brennfleck.jarod.tmr.scripts.world.Block;
import brennfleck.jarod.tmr.scripts.world.Location;

public class BlockPowerEvent extends BlockEvent {
	private int power;
	
	public BlockPowerEvent(Location l, Block b, int power) {
		super(l, b);
		this.power = power;
	}
	
	public int getPower() {
		return power;
	}
}