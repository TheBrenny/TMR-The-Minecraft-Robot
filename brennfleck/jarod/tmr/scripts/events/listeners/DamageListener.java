package brennfleck.jarod.tmr.scripts.events.listeners;

import brennfleck.jarod.tmr.scripts.events.damage.DamageEvent;
import brennfleck.jarod.tmr.scripts.events.damage.EntityDamageEvent;
import brennfleck.jarod.tmr.scripts.events.damage.WorldDamageEvent;

public interface DamageListener {
	public void onAnyDamageRecieved(DamageEvent e);
	public void onWorldDamageRecieved(WorldDamageEvent e);
	public void onEntityDamageRecieved(EntityDamageEvent e);
}