package brennfleck.jarod.tmr.scripts.events.listeners;

import brennfleck.jarod.tmr.scripts.events.TMRListener;
import brennfleck.jarod.tmr.scripts.events.blocks.BlockChangedEvent;
import brennfleck.jarod.tmr.scripts.events.blocks.BlockMetadataChangedEvent;
import brennfleck.jarod.tmr.scripts.events.blocks.BlockPowerEvent;

public interface BlockListener extends TMRListener {
	public void onBlockChanged(BlockChangedEvent e);
	public void onBlockPowerChanged(BlockPowerEvent e);
	public void onBlockMetadataChanged(BlockMetadataChangedEvent e);
}