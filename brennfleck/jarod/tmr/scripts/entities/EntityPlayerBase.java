package brennfleck.jarod.tmr.scripts.entities;

import net.minecraft.entity.player.EntityPlayer;
import brennfleck.jarod.helpfulthings.BrennyHelpful;
import brennfleck.jarod.tmr.scripts.items.PlayerInventory;

/**
 * The base for all entities that are players.
 * 
 * @author Jarod Brennfleck
 */
public abstract class EntityPlayerBase extends Entity {
	public static final int SOUTH = 200;
	public static final int WEST = 201;
	public static final int NORTH = 202;
	public static final int EAST = 203;
	public static final String EYE = "eye";
	public static final String FEET = "feet";
	private PlayerInventory inventory;
	
	public EntityPlayerBase(EntityPlayer e) {
		super(e);
	}
	
	public EntityPlayer getPlayerBase() {
		return (EntityPlayer) theRealEntity;
	}
	
	/**
	 * Returns the theoretical compass bearing this entity is currently facing.
	 */
	public int getFacingBearing() {
		return (BrennyHelpful.MathHelpful.floor_double((double) (getHeadAngles()[0] * 4.0F / 360.0F) + 0.5D) & 3) + 200;
	}
	
	/**
	 * Returns the head angles in a float array with the yaw being index[0], and
	 * the pitch being index[1].
	 */
	public float[] getHeadAngles() {
		return new float[] {theRealEntity.rotationYaw, theRealEntity.rotationPitch};
	}
	
	/**
	 * Returns whether or not this player is sprinting.
	 */
	public boolean isSprinting() {
		return getPlayerBase().isSprinting();
	}
	
	/**
	 * Returns whether or not this player is sneaking.
	 */
	public boolean isSneaking() {
		return getPlayerBase().isSneaking();
	}
	
	/**
	 * Returns whether or not this player is jumping.
	 */
	public boolean isJumping() {
		return getPlayerBase().isJumping;
	}
	
	/**
	 * Returns the inventory of this player.
	 */
	public PlayerInventory getInventory() {
		return inventory != null ? inventory : (inventory = new PlayerInventory(this));
	}

	public static double getMaximumReach() {
		return 4.5;
	}
}