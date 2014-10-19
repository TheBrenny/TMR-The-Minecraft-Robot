package brennfleck.jarod.tmr.scripts.events;

import java.util.Vector;

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
	public void handleChatMessage(String sender, String message) {
		setChanged();
		notifyListeners(sender, message);
	}
	public void notifyListeners() {
		notifyListeners("", "");
	}
	public void notifyListeners(String sender, String message) {
		Object[] arrLocal;
		synchronized(this) {
			if(!changed) return;
			arrLocal = obs.toArray();
			clearChanged();
		}
		for(int i = arrLocal.length - 1; i >= 0; i--) {
			if(arrLocal[i] instanceof ChatListener) ((ChatListener) arrLocal[i]).onChatEvent(new ChatEvent(sender, message));
		}
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
}