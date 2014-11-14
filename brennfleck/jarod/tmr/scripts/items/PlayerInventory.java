package brennfleck.jarod.tmr.scripts.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import brennfleck.jarod.tmr.scripts.entities.ControlledPlayer;
import brennfleck.jarod.tmr.scripts.entities.EntityPlayerBase;

public class PlayerInventory implements ItemHolder {
	private EntityPlayerBase owner;
	private Item[][] inventoryItems;
	private Item[] armorItems;
	
	public InventoryPlayer getRealInventory() {
		return ((EntityPlayer) owner.getRealEntity()).inventory;
	}
	
	/**
	 * Creates an inventory based from the passed
	 * {@link brennfleck.jarod.tmr.scripts.entities.EntityPlayerBase#EntityPlayerBase
	 * entity}.
	 */
	public PlayerInventory(EntityPlayerBase e) {
		this.owner = e;
		updateItems();
	}
	
	public String getName() {
		return owner.getName() + "_inventory";
	}
	
	public int getMaxSize() {
		return 36;
	}
	
	/**
	 * Returns the amount of amour that can be worn: 4.
	 */
	public static int getArmourInventorySize() {
		return 4;
	}
	
	public Item getItemInSlot(int slotX, int slotY) {
		updateItems();
		while(slotX >= 9) {
			slotY++;
			slotX -= 9;
		}
		slotY %= 4;
		return inventoryItems[slotY][slotX];
	}
	public Item getItemInArmourSlot(int slot) {
		updateItems();
		slot %= getArmourInventorySize();
		return armorItems[slot];
	}
	
	public void swapItems(int slotX, int slotY, int slotX2, int slotY2) {
		if(canPlayerInteractWith(ControlledPlayer.getPlayer())) {
			Item i = new Item(getRealInventory().getStackInSlot(slotY * 9 + slotX));
			Item j = new Item(getRealInventory().getStackInSlot(slotY2 * 9 + slotX2));
			getRealInventory().setInventorySlotContents(slotY * 9 + slotX, j.getRealItem());
			getRealInventory().setInventorySlotContents(slotY2 * 9 + slotX2, i.getRealItem()); // Still POC - Don't know if it works or not...
		}
	}
	
	public boolean canPlayerInteractWith(EntityPlayerBase e) {
		return e == ControlledPlayer.getPlayer();
	}
	
	/**
	 * Returns the currently held item.
	 */ 
	public Item getHeldItem() {
		return new Item(getRealInventory().getCurrentItem());
	}
	
	public void updateItems() {
		int x;
		for(int y = 0; y < 3; y++) {
			for(x = 0; x < 9; x++) {
				inventoryItems[y][x] = new Item(getRealInventory().mainInventory[y * 9 + x]);
			}
		}
		for(x = 0; x < 4; x++) {
			armorItems[x] = new Item(getRealInventory().armorItemInSlot(x));
		}
	}
}