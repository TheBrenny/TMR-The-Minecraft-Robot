package brennfleck.jarod.tmr.scripts.examples;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import brennfleck.jarod.tmr.scripts.Script;
import brennfleck.jarod.tmr.scripts.ScriptManifest;
import brennfleck.jarod.tmr.scripts.ScriptManifest.Category;
import brennfleck.jarod.tmr.scripts.entities.ControlledPlayer;
import brennfleck.jarod.tmr.scripts.events.blocks.BlockChangedEvent;
import brennfleck.jarod.tmr.scripts.events.blocks.BlockPowerEvent;
import brennfleck.jarod.tmr.scripts.events.chat.ChatEvent;
import brennfleck.jarod.tmr.scripts.events.damage.DamageEvent;
import brennfleck.jarod.tmr.scripts.events.damage.EntityDamageEvent;
import brennfleck.jarod.tmr.scripts.events.damage.WorldDamageEvent;
import brennfleck.jarod.tmr.scripts.events.listeners.BlockListener;
import brennfleck.jarod.tmr.scripts.events.listeners.ChatListener;
import brennfleck.jarod.tmr.scripts.events.listeners.DamageListener;

@ScriptManifest(name = "Listen To This", author = "Jarod Brennfleck", version = "1.0", category = Category.GENERAL)
public class ListenToThis extends Script {
	public boolean onStart() {
		addEventListener(new Events());
		return true;
	}
	
	public long loop() {
		return 15000;
	}
	
	public static class Events implements ChatListener, BlockListener, DamageListener {
		public static final int[] blocksToListen = {Block.getIdFromBlock(Blocks.piston), Block.getIdFromBlock(Blocks.sticky_piston), Block.getIdFromBlock(Blocks.iron_door)};
		public static final int[] changedBlocks = {};
		
		public void onAnyDamageRecieved(DamageEvent e) {
			ControlledPlayer.sendChatMessage("OUCH!");
		}
		
		public void onWorldDamageRecieved(WorldDamageEvent e) {
			ControlledPlayer.sendChatMessage("WHY WORLD, WHY?");
		}
		
		public void onEntityDamageRecieved(EntityDamageEvent e) {
			ControlledPlayer.sendChatMessage("I'm going to kill you " + e.getCulprit().getName() + "!");
		}
		
		public void onBlockChanged(BlockChangedEvent e) {
			ControlledPlayer.sendChatMessage("I heard that block " + e.getPreviousBlock().getName() + " changed to " + e.getBlock().getName() + "...");
		}
		
		public void onBlockPowerChanged(BlockPowerEvent e) {
			ControlledPlayer.sendChatMessage("Power up " + e.getBlock().getName() + " to power level " + e.getPower() + ".");
		}
		
		public boolean respondWhenBlockChanged(int id) {
			for(int a : changedBlocks)
				if(id == a) return true;
			return false;
		}
		
		public boolean respondWhenPowerGivenTo(int id) {
			for(int a : blocksToListen)
				if(id == a) return true;
			return false;
		}
		
		public void onChatEvent(ChatEvent e) {
			if(!e.getSender().equals(ControlledPlayer.getPlayer().getName())) ControlledPlayer.sendChatMessage("I heard that, " + e.getSender() + ".");
		}
	}
}