package brennfleck.jarod.tmr.scripts.items;

import net.minecraft.block.BlockChest;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import brennfleck.jarod.tmr.scripts.entities.ControlledPlayer;
import brennfleck.jarod.tmr.scripts.entities.EntityPlayerBase;
import brennfleck.jarod.tmr.scripts.world.Area;
import brennfleck.jarod.tmr.scripts.world.Location;
import brennfleck.jarod.tmr.scripts.world.World;

public class ChestInventory implements ItemHolder {
	private BlockChest blockChest;
	private Location location;
	private Item[][] inventory;
	
	public IInventory getRealInventory() {
		return blockChest.getChestInventory(Minecraft.getMinecraft().theWorld, (int) location.getX(), (int) location.getY(), (int) location.getZ());
	}
	
	public ChestInventory(BlockChest bc, Location l) {
		this.blockChest = bc;
		this.location = l;
		updateItems();
	}
	
	public void swapItems(int slotX, int slotY, int slotX2, int slotY2) {
		if(canPlayerInteractWith(ControlledPlayer.getPlayer())) {
			Item i = new Item(getRealInventory().getStackInSlot(slotY * 9 + slotX));
			Item j = new Item(getRealInventory().getStackInSlot(slotY2 * 9 + slotX2));
			getRealInventory().setInventorySlotContents(slotY * 9 + slotX, j.getRealItem());
			getRealInventory().setInventorySlotContents(slotY2 * 9 + slotX2, i.getRealItem()); // Still POC - Don't know if it works or not...
		}
	}
	
	public String getName() {
		return "chest_:" + location.toString();
	}
	
	public int getMaxSize() {
		return getRealInventory().getSizeInventory();
	}
	
	public Item getItemInSlot(int slotX, int slotY) {
		updateItems();
		return new Item(getRealInventory().getStackInSlot(slotY * 9 + slotX));
	}
	
	public boolean canPlayerInteractWith(EntityPlayerBase e) {
		return e.getDistanceToPreciseLocation("eye", location) < EntityPlayerBase.getMaximumReach();
	}
	
	public void updateItems() {
		for(int y = 0; y < getRealInventory().getSizeInventory() / 9; y++) {
			for(int x = 0; x < 9; x++) {
				Item i = getItemInSlot(x, y);
				if(i == null) inventory[y][x] = null;
				else inventory[y][x] = i;
			}
		}
	}
}