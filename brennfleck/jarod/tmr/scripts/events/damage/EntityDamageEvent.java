package brennfleck.jarod.tmr.scripts.events.damage;

import brennfleck.jarod.tmr.scripts.entities.Entity;

public class EntityDamageEvent extends DamageEvent {
	private Entity culprit;
	
	public EntityDamageEvent(DamageType damageType, Entity culprit, float amount) {
		super(damageType, amount);
		this.culprit = culprit;
	}
	
	public Entity getCulprit() {
		return this.culprit;
	}
}
