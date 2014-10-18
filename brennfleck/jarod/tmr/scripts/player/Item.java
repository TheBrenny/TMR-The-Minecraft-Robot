package brennfleck.jarod.tmr.scripts.player;

import net.minecraft.item.ItemStack;

public class Item {
	private String unlocalName;
	private String displayName;
	private int id;
	private int stackCount;
	private int itemDamage;
	private ItemType itemType;
	
	private Item(ItemStack is) {
		this.unlocalName = is.getUnlocalizedName();
		this.displayName = is.getDisplayName();
		this.id = net.minecraft.item.Item.getIdFromItem(is.getItem());
		this.stackCount = is.stackSize;
		this.itemType = ItemType.getItemType(this);
	}
	
	/**
	 * Constructs an Item by the id.
	 */
	public Item(int id) {
		this(id, 1, 0);
	}
	
	/**
	 * Constructs an Item by the id and stack count.
	 */
	public Item(int id, int stackCount) {
		this(id, stackCount, 0);
	}
	
	/**
	 * Constructs an Item by the id, stack count, and damage amount.
	 */
	public Item(int id, int stackCount, int damageAmount) {
		this(new ItemStack(net.minecraft.item.Item.getItemById(id), stackCount, damageAmount));
	}
	
	/**
	 * Returns the display name if the parameter is true, or the unlocal name if
	 * the parameter is false.
	 */
	public String getName(boolean displayName) {
		if(displayName) return this.displayName;
		return this.unlocalName;
	}
	
	/**
	 * Returns the item id.
	 */
	public int getID() {
		return this.id;
	}
	
	/**
	 * Returns the stack count.
	 */
	public int getStackCount() {
		return this.stackCount;
	}
	
	/**
	 * Returns the {@link ItemType#ItemType ItemType}.
	 */
	public ItemType getItemType() {
		return this.itemType;
	}
	
	/**
	 * Determines the item type of an item.
	 * 
	 * @author Jarod Brennfleck
	 */
	public enum ItemType {
		SHOVEL,
		HOE,
		PICKAXE,
		AXE,
		SWORD,
		ARMOUR,
		FOOD,
		OTHER;
		
		/**
		 * Finds out whether this item type is in the list of item types passed.
		 */
		public boolean isAnyOf(ItemType... its) {
			for(ItemType it : its)
				if(it == this) return true;
			return false;
		}
		
		/**
		 * Determines the {@link #ItemType} based off the name of the unlocal
		 * item name.
		 */
		public static ItemType getItemType(Item i) {
			String name = i.getName(false).toLowerCase();
			if(name.contains("shovel")) return ItemType.SHOVEL;
			if(name.contains("hoe")) return ItemType.HOE;
			if(name.contains("pickaxe")) return ItemType.PICKAXE;
			if(name.contains("hatchet")) return ItemType.AXE;
			if(name.contains("helmet") || name.contains("chestplate") || name.contains("leggings") || name.contains("boots")) return ItemType.ARMOUR;
			if(name.contains("sword")) return ItemType.SWORD;
			if(name.contains("apple") || name.contains("mushroomstew") || name.contains("bread") || name.contains("porkchop")) return ItemType.FOOD;
			if(name.contains("melon") || name.contains("beef") || name.contains("chicken") || name.contains("cookie")) return ItemType.FOOD;
			if(name.contains("spidereye") || (name.contains("potion") && !name.contains("splash"))) return ItemType.FOOD;
			if((name.contains("carrot") && !name.contains("stick")) || name.contains("potato") || name.contains("fish")) return ItemType.FOOD;
			if(name.contains("pumpkinpie") || name.contains("rottenflesh") || name.contains("milk")) return ItemType.FOOD;
			return ItemType.OTHER;
		}
	}
}
