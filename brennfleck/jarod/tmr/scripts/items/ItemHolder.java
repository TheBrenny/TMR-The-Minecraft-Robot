package brennfleck.jarod.tmr.scripts.items;

import brennfleck.jarod.tmr.scripts.entities.EntityPlayerBase;

public interface ItemHolder {
	/**
	 * Returns the name of this ItemHolder.
	 */
	public String getName();
	
	/**
	 * Returns the maximum amount of items allowed in this ItemHolder.
	 */
	public int getMaxSize();
	
	/**
	 * Gets the item in a specified slot of this ItemHolder.
	 */
	public Item getItemInSlot(int slotX, int slotY);
	
	/**
	 * Returns whether or not the player can interact with this ItemHolder
	 */
	public boolean canPlayerInteractWith(EntityPlayerBase e);
	
	/**
	 * Updates the local inventory so it is a clone of the real inventory owned
	 * by the real entity of the owner of this inventory.
	 */
	public void updateItems();
	
	/**
	 * Swaps the items at the given positions.
	 */
	public void swapItems(int slotX, int slotY, int slotX2, int slotY2);
}