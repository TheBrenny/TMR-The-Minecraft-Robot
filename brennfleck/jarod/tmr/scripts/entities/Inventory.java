package brennfleck.jarod.tmr.scripts.entities;

import brennfleck.jarod.tmr.utils.TmrInputProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0EPacketClickWindow;

public class Inventory {
	private static InventoryPlayer getInventory() {
		return Minecraft.getMinecraft().thePlayer.inventory;
	}
	
	private static EntityClientPlayerMP getPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}
	
	/**
	 * Swaps the items at the coordinates given.
	 */
	public static void swapItemsAt(int xA, int yA, int xB, int yB) {
		int positionA = yA * 9 + xA;
		int positionB = yB * 9 + xB;
		if(positionA < 0 || positionA >= 36 || positionB < 0 || positionB >= 36) return;
		ItemStack[] inv = getInventory().mainInventory;
		getPlayer().sendQueue.addToSendQueue(new C0EPacketClickWindow(getPlayer().inventoryContainer.windowId, positionA, 0, 0, inv[positionA], getPlayer().inventoryContainer.getNextTransactionID(null)));
		getPlayer().sendQueue.addToSendQueue(new C0EPacketClickWindow(getPlayer().inventoryContainer.windowId, positionB, 0, 0, inv[positionB], getPlayer().inventoryContainer.getNextTransactionID(null)));
		getPlayer().sendQueue.addToSendQueue(new C0EPacketClickWindow(getPlayer().inventoryContainer.windowId, positionA, 0, 0, null, getPlayer().inventoryContainer.getNextTransactionID(null)));
		ItemStack tmp = inv[positionA];
		inv[positionA] = inv[positionB];
		inv[positionB] = tmp;
		getInventory().mainInventory = inv;
	}
	
	/**
	 * Sets the active hotbar index to the next index to the right.
	 */
	public static Item switchToNextItem() {
		return setActiveItem(getHotbarSelectedIndex() + 1);
	}
	
	/**
	 * Sets the active hotbar index to the passed parameter.
	 */
	public static Item setActiveItem(int index) {
		index %= 9;
		getInventory().currentItem = index;
		return getHotbarSelectedItem();
	}
	
	/**
	 * Returns the currently selected hotbar index.
	 */
	public static int getHotbarSelectedIndex() {
		return getInventory().currentItem;
	}
	
	/**
	 * Returns the item held in the selected index of the hotbar.
	 */
	public static Item getHotbarSelectedItem() {
		ItemStack is = getInventory().getCurrentItem();
		return new Item((is != null ? is.getItem().getIdFromItem(is.getItem()) : 0));
	}
	
	/**
	 * Returns all items in the hotbar.
	 */
	public static Item[] getItemsInHotbar() {
		return getItemsInInventory()[0];
	}
	
	/**
	 * Returns all items in the {@link #getItemsInMainInventory() main
	 * inventory} and sorts the items into a 2D array.
	 */
	public static Item[][] getItemsInInventory() {
		Item[][] items = new Item[4][9];
		int x = 0, y = 0;
		for(ItemStack is : getInventory().mainInventory) {
			if(is != null) items[y][x] = new Item(is.getItem().getIdFromItem(is.getItem()));
			x++;
			if(x >= 9) {
				x = 0;
				y++;
			}
		}
		return items;
	}
	
	/**
	 * Returns all items in the main inventory, unsorted. For a sorted 2D array,
	 * use {@link #getItemsInInventory()}.
	 */
	public static Item[] getItemsInMainInventory() {
		Item[] items = new Item[36];
		for(int i = 0; i < items.length; i++) {
			ItemStack is = getInventory().mainInventory[i];
			if(is != null) items[i] = new Item(is.getItem().getIdFromItem(is.getItem()));
		}
		return items;
	}
	
	/**
	 * Returns the armour being worn.
	 */
	public static Item[] getArmorEquipped() {
		Item[] items = new Item[4];
		for(int i = 0; i < items.length; i++) {
			ItemStack is = getInventory().armorInventory[i];
			if(is != null) items[i] = new Item(is.getItem().getIdFromItem(is.getItem()));
		}
		return items;
	}
	
	/**
	 * Checks whether or not the hotbar contains an <code>itemType</code>.
	 */
	public static boolean hotbarContains(Item.ItemType itemType) {
		for(Item item : getItemsInHotbar()) {
			if(item != null && item.getItemType() == itemType) return true;
		}
		return false;
	}
	
	/**
	 * Returns the item at the given location.
	 */
	public static Item getItemAt(int x, int y) {
		return getItemsInInventory()[y][x];
	}
	
	/**
	 * Checks whether or not the inventory contains a specific itemType.
	 */
	public static boolean contains(Item.ItemType itemType) {
		for(Item item : getItemsInMainInventory())
			if(item != null && item.getItemType() == itemType) return true;
		return false;
	}
	
	/**
	 * Returns the {@link #getFirstOccurrence(Item) first occurrence} of an item
	 * as an integer array where the x-coordinate of the item is index[0] and
	 * the y-coordinate is index[1].
	 * 
	 * @see {@link #getFirstOccurrence(Item)}
	 * @see {@link #getFirstOccurrencePoint(Item.ItemType)}
	 */
	public static int[] getFirstOccurrencePoint(Item item) {
		int i = getFirstOccurrence(item);
		return new int[] {i % 9, i / 9};
	}
	
	/**
	 * Returns the {@link #getFirstOccurrence(Item.ItemType) first occurrence}
	 * of an item by it's item type as an integer array where the x-coordinate
	 * of the item is index[0] and the y-coordinate is index[1].
	 * 
	 * @see {@link #getFirstOccurrence(Item.ItemType)}
	 * @see {@link #getFirstOccurrencePoint(Item)}
	 */
	public static int[] getFirstOccurrencePoint(Item.ItemType itemType) {
		int i = getFirstOccurrence(itemType);
		return new int[] {i % 9, i / 9};
	}
	
	/**
	 * Returns the first occurrence of an item in the main inventory.
	 * 
	 * @see {@link #getFirstOccurrence(Item.ItemType)}
	 */
	public static int getFirstOccurrence(Item item) {
		if(!contains(item.getItemType())) return -1;
		for(int i = 0; i < getItemsInMainInventory().length; i++)
			if(item.equals(getItemsInMainInventory()[i])) return i;
		return -1;
	}
	
	/**
	 * Returns the first occurrence of an item by it's item type in the main
	 * inventory.
	 * 
	 * @see {@link #getFirstOccurrence(Item)}
	 */
	public static int getFirstOccurrence(Item.ItemType itemType) {
		if(!contains(itemType)) return -1;
		for(int i = 0; i < getItemsInMainInventory().length; i++)
			if(getItemsInMainInventory()[i] != null && itemType == getItemsInMainInventory()[i].getItemType()) return i;
		return -1;
	}
}