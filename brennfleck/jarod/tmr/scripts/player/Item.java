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
		this.itemType = ItemType.getItemType(is);
	}
	public Item(int id) {
		this(id, 1, 0);
	}
	public Item(int id, int stackCount) {
		this(id, stackCount, 0);
	}
	public Item(int id, int stackCount, int damageAmount) {
		this(new ItemStack(net.minecraft.item.Item.getItemById(id), stackCount, damageAmount));
	}
	
	public String getName(boolean displayName) {
		if(displayName) return this.displayName;
		return this.unlocalName;
	}
	
	public int getID() {
		return this.id;
	}
	
	public int getStackCount() {
		return this.stackCount;
	}
	
	public ItemType getItemType() {
		return this.itemType;
	}
	
	public enum ItemType {
		SHOVEL,
		HOE,
		PICKAXE,
		AXE,
		SWORD,
		ARMOUR,
		FOOD,
		OTHER;
		
		public boolean isAnyOf(ItemType ... types) {
			for(ItemType a : types) {
				for(ItemType b : ItemType.values()) {
					if(a == b) return true;
				}
			}
			return false;
		}
		
		public static ItemType getItemType(ItemStack is) {
			String name = is.getUnlocalizedName().toLowerCase();
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
