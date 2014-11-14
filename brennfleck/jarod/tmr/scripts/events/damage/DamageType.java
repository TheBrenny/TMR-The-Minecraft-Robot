package brennfleck.jarod.tmr.scripts.events.damage;

public enum DamageType {
	OTHER("other"),
	IN_FIRE("inFire"),
	ON_FIRE("onFire"),
	LAVA("lava"),
	WALL("wall"),
	DROWN("drown"),
	STARVE("starve"),
	CACTUS("cactus"),
	FALL("fall"),
	OUT_OF_WORLD("outOfWorld"),
	GENERIC("generic"),
	MAGIC("magic"),
	WITHER("wither"),
	ANVIL("anvil"),
	FALLING_BLOCK("fallingBlock");
	
	private String type;
	
	private DamageType(String type) {
		this.type = type;
	}
	
	public static DamageType getDamageType(String type) {
		for(DamageType dt : DamageType.values()) {
			if(type.equals(dt.type)) return dt;
		}
		return OTHER;
	}
}