package brennfleck.jarod.tmr.scripts.events.damage;

public abstract class DamageEvent {
	private DamageType damageType;
	private float damageAmount;
	
	public DamageEvent(DamageType damageType, float damageAmount) {
		this.damageType = damageType;
	}
	
	public DamageType getDamageType() {
		return this.damageType;
	}
	
	public float getDamageAmount() {
		return this.damageAmount;
	}
}