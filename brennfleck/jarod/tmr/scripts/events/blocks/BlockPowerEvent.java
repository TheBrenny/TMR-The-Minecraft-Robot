package brennfleck.jarod.tmr.scripts.events.blocks;

import brennfleck.jarod.tmr.scripts.world.Block;
import brennfleck.jarod.tmr.scripts.world.Location;

public class BlockPowerEvent extends BlockEvent {
	private float power;
	
	public BlockPowerEvent(Location l, Block b, float power) {
		super(l, b);
		this.power = power;
	}
	
	public float getPower() {
		return power;
	}
}