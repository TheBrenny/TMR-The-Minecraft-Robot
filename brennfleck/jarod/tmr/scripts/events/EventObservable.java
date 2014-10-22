package brennfleck.jarod.tmr.scripts.events;

import java.util.Vector;

import brennfleck.jarod.tmr.scripts.events.blocks.BlockChangedEvent;
import brennfleck.jarod.tmr.scripts.events.blocks.BlockPowerEvent;
import brennfleck.jarod.tmr.scripts.events.chat.ChatEvent;
import brennfleck.jarod.tmr.scripts.events.listeners.BlockListener;
import brennfleck.jarod.tmr.scripts.events.listeners.ChatListener;
import brennfleck.jarod.tmr.scripts.world.Block;
import brennfleck.jarod.tmr.scripts.world.Location;

public final class EventObservable {
	private boolean changed = false;
	private Vector<TMRListener> obs;
	
	public EventObservable() {
		obs = new Vector<TMRListener>();
	}
	
	public synchronized void addListener(TMRListener l) {
		if(l == null) throw new NullPointerException();
		if(!obs.contains(l)) {
			obs.addElement(l);
		}
	}
	
	public synchronized void deleteListener(TMRListener l) {
		obs.removeElement(l);
	}
	
	public void notifyListeners() {
		notifyListeners();
	}
	
	public void notifyListeners(Object o) {
		Object[] arrLocal;
		synchronized(this) {
			if(!changed) return;
			arrLocal = obs.toArray();
			clearChanged();
		}
	}
	
	public Object[] getListeners() {
		Object[] arrLocal;
		synchronized(this) {
			if(!changed) return null;
			arrLocal = obs.toArray();
			clearChanged();
		}
		return arrLocal;
	}
	
	public synchronized void deleteListeners() {
		obs.removeAllElements();
	}
	
	protected synchronized void setChanged() {
		changed = true;
	}
	
	protected synchronized void clearChanged() {
		changed = false;
	}
	
	public synchronized boolean hasChanged() {
		return changed;
	}
	
	public synchronized int countListeners() {
		return obs.size();
	}
	
	public void handleChatMessage(String sender, String message) {
		setChanged();
		Object[] arr = getListeners();
		for(int i = arr.length - 1; i >= 0; i--) {
			if(arr[i] instanceof ChatListener) ((ChatListener) arr[i]).onChatEvent(new ChatEvent(sender, message));
		}
	}
	
	public void handleBlockChanged(Location l, Block oldB, Block newB) {
		setChanged();
		boolean blockChange = true;
		if(oldB.getID() == newB.getID() && oldB.getMetadata() != newB.getMetadata()) blockChange = false;
		Object[] arr = getListeners();
		for(int i = arr.length - 1; i >= 0; i--) {
			if(arr[i] instanceof BlockListener) ((BlockListener) arr[i]).onBlockChanged(new BlockChangedEvent(l, oldB, newB));
		}
	}
	
	public void handlePowerChange(Location l, Block b, int power) {
		Object[] arr = getListeners();
		for(int i = arr.length - 1; i >= 0; i--) {
			if(arr[i] instanceof BlockListener) ((BlockListener) arr[i]).onBlockPowerChanged(new BlockPowerEvent(l, b, power));
		}
	}
}