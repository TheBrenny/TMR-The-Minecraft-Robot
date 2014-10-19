package brennfleck.jarod.tmr.scripts.entities;

public enum EntityType {
	PLAYER,
	ITEM,
	EFFECT,
	AGGRESSIVE,
	NEUTRAL,
	PASSIVE,
	FOLLOWER;
	EntityType() {}
	
	public boolean isAnyOf(EntityType... ets) {
		for(EntityType et : ets)
			if(et == this) return true;
		return false;
	}
}