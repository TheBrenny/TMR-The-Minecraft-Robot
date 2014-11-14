package brennfleck.jarod.tmr.scripts.events;

import java.util.Vector;

import brennfleck.jarod.tmr.scripts.entities.Entity;
import brennfleck.jarod.tmr.scripts.events.blocks.BlockChangedEvent;
import brennfleck.jarod.tmr.scripts.events.blocks.BlockPowerEvent;
import brennfleck.jarod.tmr.scripts.events.chat.ChatEvent;
import brennfleck.jarod.tmr.scripts.events.damage.DamageType;
import brennfleck.jarod.tmr.scripts.events.damage.EntityDamageEvent;
import brennfleck.jarod.tmr.scripts.events.damage.WorldDamageEvent;
import brennfleck.jarod.tmr.scripts.events.listeners.BlockListener;
import brennfleck.jarod.tmr.scripts.events.listeners.ChatListener;
import brennfleck.jarod.tmr.scripts.events.listeners.DamageListener;
import brennfleck.jarod.tmr.scripts.events.listeners.TMRListener;
import brennfleck.jarod.tmr.scripts.world.Block;
import brennfleck.jarod.tmr.scripts.world.Location;

public class EventObservable {
	private static EventObservable eventObservable;
	private boolean changed = false;
	private Vector<TMRListener> obs;
	
	public EventObservable() {
		obs = new Vector<TMRListener>();
		eventObservable = this;
	}
	
	public final synchronized void addListener(TMRListener l) {
		if(l == null) throw new NullPointerException();
		if(!obs.contains(l)) {
			obs.addElement(l);
		}
	}
	
	public final synchronized void deleteListener(TMRListener l) {
		obs.removeElement(l);
	}
	
	public final synchronized void notifyListeners() {
		notifyListeners();
	}
	
	public final synchronized void notifyListeners(Object o) {
		Object[] arrLocal;
		synchronized(this) {
			if(!changed) return;
			arrLocal = obs.toArray();
			clearChanged();
		}
	}
	
	public final synchronized Object[] getListeners() {
		Object[] arrLocal;
		synchronized(this) {
			if(!changed) return null;
			arrLocal = obs.toArray();
			clearChanged();
		}
		return arrLocal;
	}
	
	public final synchronized void deleteListeners() {
		obs.removeAllElements();
	}
	
	protected final synchronized void setChanged() {
		changed = true;
	}
	
	protected final synchronized void clearChanged() {
		changed = false;
	}
	
	public final synchronized boolean hasChanged() {
		return changed;
	}
	
	public final synchronized int countListeners() {
		return obs.size();
	}
	
	public final synchronized void handleChatMessage(String sender, String message) {
		setChanged();
		Object[] arr = getListeners();
		for(int i = arr.length - 1; i >= 0; i--) {
			if(arr[i] instanceof ChatListener) ((ChatListener) arr[i]).onChatEvent(new ChatEvent(sender, message));
		}
	}
	
	public final synchronized void handleBlockChanged(Location l, Block oldB, Block newB) {
		setChanged();
		Object[] arr = getListeners();
		for(int i = arr.length - 1; i >= 0; i--) {
			if(arr[i] instanceof BlockListener) {
				BlockListener bl = ((BlockListener) arr[i]);
				System.out.println("Should respond: " + (bl.respondWhenBlockChanged(oldB.getID()) && bl.respondWhenBlockChanged(newB.getID())));
				if(bl.respondWhenBlockChanged(oldB.getID()) || bl.respondWhenBlockChanged(newB.getID())) bl.onBlockChanged(new BlockChangedEvent(l, oldB, newB));
			}
		}
	}
	
	public final synchronized void handlePowerChange(Location l, Block b, float power) {
		setChanged();
		Object[] arr = getListeners();
		for(int i = arr.length - 1; i >= 0; i--) {
			if(arr[i] instanceof BlockListener) {
				BlockListener bl = ((BlockListener) arr[i]);
				System.out.println("Should respond: " + bl.respondWhenPowerGivenTo(b.getID()));
				if(bl.respondWhenPowerGivenTo(b.getID())) bl.onBlockPowerChanged(new BlockPowerEvent(l, b, power));
			}
		}
	}
	
	public final synchronized void handleDamageFromWorld(DamageType dType, float amount) {
		setChanged();
		Object[] arr = getListeners();
		for(int i = arr.length - 1; i >= 0; i--) {
			if(arr[i] instanceof DamageListener) {
				((DamageListener) arr[i]).onAnyDamageRecieved(new WorldDamageEvent(dType, amount));
				((DamageListener) arr[i]).onWorldDamageRecieved(new WorldDamageEvent(dType, amount));
			}
		}
	}
	
	public final synchronized void handleDamageFromEntity(DamageType dType, Entity e, float amount) {
		setChanged();
		Object[] arr = getListeners();
		for(int i = arr.length - 1; i >= 0; i--) {
			if(arr[i] instanceof DamageListener) {
				((DamageListener) arr[i]).onAnyDamageRecieved(new EntityDamageEvent(dType, e, amount));
				((DamageListener) arr[i]).onEntityDamageRecieved(new EntityDamageEvent(dType, e, amount));
			}
		}
	}
	
	public final static EventObservable getObservable() {
		return eventObservable;
	}
}