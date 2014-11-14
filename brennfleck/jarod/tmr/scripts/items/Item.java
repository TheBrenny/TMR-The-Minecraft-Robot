package brennfleck.jarod.tmr.scripts.items;

import java.util.List;

import net.minecraft.item.ItemStack;

public class Item {
	private ItemStack theRealItem;
	
	public Item(ItemStack is) {
		this.theRealItem = is;
	}
	
	/**
	 * Returns the tooltip that is seen in the inventory.
	 */
	public List getTooltip() {
		return theRealItem.getTooltip(null, false);
	}
	
	/**
	 * Returns the cost to repair this item.
	 */
	public int getRepairCost() {
		return theRealItem.getRepairCost();
	}
	
	/**
	 * Returns the display name of this item.
	 */
	public String getDisplayName() {
		return theRealItem.getDisplayName();
	}
	
	/**
	 * Returns the unlocalized name of this item, ie, the name referred to from
	 * the code.
	 */
	public String getUnlnocalizedName() {
		return theRealItem.getUnlocalizedName();
	}
	
	/**
	 * Returns the damage received by this item.
	 */
	public int getItemDamage() {
		return theRealItem.getItemDamage();
	}
	
	/**
	 * Returns the {@link ItemUseAction#ItemUseAction ItemUseAction} of this
	 * item.
	 */
	public ItemUseAction getUseAction() {
		return ItemUseAction.getAppropriate(theRealItem.getItemUseAction());
	}
	
	/**
	 * Returns the current stack size of this item.
	 */
	public int getItemStackSize() {
		return theRealItem.stackSize;
	}
	
	/**
	 * Returns the maximum amount of damage this item can receive.
	 */
	public int getMaxDamage() {
		return theRealItem.getMaxDamage();
	}
	
	/**
	 * Returns the maximum stack size for this item.
	 */
	public int getMaxStackSize() {
		return theRealItem.getMaxStackSize();
	}
	
	/**
	 * Returns the maximum time this item can be used for.
	 */
	public int getMaxItemUseDuration() {
		return theRealItem.getMaxItemUseDuration();
	}
	
	/**
	 * Returns the {@link ItemRarity#ItemRarity ItemRarity} of this item.
	 */
	public ItemRarity getRarity() {
		return ItemRarity.getAppropriate(theRealItem.getRarity());
	}
	
	/**
	 * Returns the item ID for this item.
	 */
	public int getItemID() {
		return net.minecraft.item.Item.getIdFromItem(theRealItem.getItem());
	}
	
	public ItemStack getRealItem() {
		return theRealItem;
	}
	
	/**
	 * Returns whether or not the given item has the same id as the given id.
	 * This is exactly the same as <code>item.getItemID() == id</code>.
	 */
	public static boolean isCorrectItemByID(Item item, int id) {
		return item.getItemID() == id;
	}
	
	/**
	 * Returns whether or not this given item has the same display name or
	 * unlocalised name, depending on the passed boolean.
	 */
	public static boolean isCorrectItemByName(Item item, String name, boolean displayName) {
		String toUse = displayName ? item.getDisplayName() : item.getUnlnocalizedName();
		return toUse.equals(name);
	}
}