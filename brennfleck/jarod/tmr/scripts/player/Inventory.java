package brennfleck.jarod.tmr.scripts.player;

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
	
	public static void swapItemsAt(int xA, int yA, int xB, int yB) {
		int positionA = yA * 9 + xA;
		int positionB = yB * 9 + xB;
		if(positionA < 0 || positionA >= 36 || positionB < 0 || positionB >= 36) return;
		ItemStack[] inv = getInventory().mainInventory;
		getPlayer().sendQueue.addToSendQueue(new C0EPacketClickWindow(getPlayer().inventoryContainer.windowId, positionA, 0, 0, inv[positionA], getPlayer().inventoryContainer.getNextTransactionID(null)));
		getPlayer().sendQueue.addToSendQueue(new C0EPacketClickWindow(getPlayer().inventoryContainer.windowId, positionB, 0, 0, inv[positionB], getPlayer().inventoryContainer.getNextTransactionID(null)));
		getPlayer().sendQueue.addToSendQueue(new C0EPacketClickWindow(getPlayer().inventoryContainer.windowId, positionA, 0, 0, null, getPlayer().inventoryContainer.getNextTransactionID(null)));
		//ItemStack tmp = inv[positionA];
		//inv[positionA] = inv[positionB];
		//inv[positionB] = tmp;
		//getInventory().mainInventory = inv;
	}
	
	public static Item switchToNextItem() {
		return setActiveItem(getHotbarSelectedIndex() + 1);
	}
	
	public static Item setActiveItem(int index) {
		//@formatter:off
		while(index > 8) index -= 9;
		while(index < 0) index += 9;
		//@formatter:on
		getInventory().currentItem = index;
		return getHotbarSelectedItem();
	}
	
	public static int getHotbarSelectedIndex() {
		return getInventory().currentItem;
	}
	
	public static Item getHotbarSelectedItem() {
		ItemStack is = getInventory().getCurrentItem();
		return new Item((is != null ? is.getItem().getIdFromItem(is.getItem()) : 0));
	}
	
	public static Item[] getItemsInHotbar() {
		return getItemsInInventory()[0];
	}
	
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
	
	public static Item[] getItemsInMainInventory() {
		Item[] items = new Item[36];
		for(int i = 0; i < items.length; i++) {
			ItemStack is = getInventory().mainInventory[i];
			if(is != null) items[i] = new Item(is.getItem().getIdFromItem(is.getItem()));
		}
		return items;
	}
	
	public static Item[] getArmorEquipped() {
		Item[] items = new Item[4];
		for(int i = 0; i < items.length; i++) {
			ItemStack is = getInventory().armorInventory[i];
			if(is != null) items[i] = new Item(is.getItem().getIdFromItem(is.getItem()));
		}
		return items;
	}
	
	public static boolean hotbarContains(Item.ItemType itemType) {
		for(Item item : getItemsInHotbar()) {
			if(item != null && item.getItemType() == itemType) return true;
		}
		return false;
	}
	
	public static Item getItemAt(int x, int y) {
		return getItemsInInventory()[y][x];
	}
	
	public static boolean contains(Item.ItemType itemType) {
		for(Item item : getItemsInMainInventory())
			if(item != null && item.getItemType() == itemType) return true;
		return false;
	}
	
	public static int[] getFirstOccurencePoint(Item item) {
		int i = getFirstOccurence(item);
		return new int[] {i % 9, i / 9};
	}
	
	public static int[] getFirstOccurencePoint(Item.ItemType itemType) {
		int i = getFirstOccurence(itemType);
		return new int[] {i % 9, i / 9};
	}
	
	public static int getFirstOccurence(Item item) {
		if(!contains(item.getItemType())) return -1;
		for(int i = 0; i < getItemsInMainInventory().length; i++)
			if(item.equals(getItemsInMainInventory()[i])) return i;
		return -1;
	}
	
	public static int getFirstOccurence(Item.ItemType itemType) {
		if(!contains(itemType)) return -1;
		for(int i = 0; i < getItemsInMainInventory().length; i++)
			if(getItemsInMainInventory()[i] != null && itemType == getItemsInMainInventory()[i].getItemType()) return i;
		return -1;
	}
}