package brennfleck.jarod.tmr.scripts.examples;

import brennfleck.jarod.tmr.scripts.Script;
import brennfleck.jarod.tmr.scripts.ScriptManifest;
import brennfleck.jarod.tmr.scripts.ScriptManifest.Category;
import brennfleck.jarod.tmr.scripts.entities.ControlledPlayer;
import brennfleck.jarod.tmr.scripts.entities.Inventory;
import brennfleck.jarod.tmr.scripts.entities.Item;
import brennfleck.jarod.tmr.scripts.entities.Item.ItemType;
import brennfleck.jarod.tmr.scripts.minecraft.MinecraftForm;
import brennfleck.jarod.tmr.scripts.world.Block;
import brennfleck.jarod.tmr.scripts.world.Location;
import brennfleck.jarod.tmr.scripts.world.World;

@ScriptManifest(name = "Auto Quary - BROKEN", version = "0.1", author = "Jarod Brennfleck", category = Category.MINING)
public class AutoQuary extends Script {
	public boolean[][][] blocks;
	public Location startPosition;
	public Location nextPosition;
	public boolean askedReady = false;
	public boolean firstRun = true;
	public boolean isReady = false;
	public int width = 3;
	public int length = 3;
	public int depth = 3;
	public boolean pick, shovel;
	
	@Override
	public boolean onStart() {
		new Gui();
		return true;
	}
	
	public void firstRun() {
		firstRun = false;
		blocks = new boolean[this.depth][this.width][this.length];
		for(boolean[][] depth : blocks)
			for(boolean[] width : depth)
				for(int i = 0; i < width.length; i++)
					width[i] = false;
		startPosition = ControlledPlayer.getPlayer().getLocation("").shift(0, -1, 0).getNonPrecise();
	}
	
	public int[] getNextBlock() {
		for(int y = 0; y < depth; y++)
			for(int x = 0; x < width; x++)
				for(int z = 0; z < width; z++)
					if(!blocks[y][x][z]) return new int[] {y, x, z};
		return new int[] {-1};
	}
	
	public void setBlock(int[] coord, boolean state) {
		int ySpot = 0, xSpot = 1, zSpot = 2;
		blocks[coord[0]][coord[1]][coord[2]] = state;
	}
	
	@Override
	public long loop() {
		if(!isReady) return 3000;
		if(firstRun) firstRun();
		int[] pt;
		if(shovel && (Inventory.getItemAt(0, 0) == null || Inventory.getItemAt(0, 0).getItemType() != Item.ItemType.SHOVEL)) {
			pt = Inventory.getFirstOccurrencePoint(Item.ItemType.SHOVEL);
			Inventory.swapItemsAt(0, 0, pt[0], pt[1]);
		}
		if(pick && (Inventory.getItemAt(1, 0) == null || Inventory.getItemAt(1, 0).getItemType() != Item.ItemType.PICKAXE)) {
			pt = Inventory.getFirstOccurrencePoint(Item.ItemType.PICKAXE);
			Inventory.swapItemsAt(1, 0, pt[0], pt[1]);
		}
		int[] nextBlock = getNextBlock();
		if(nextBlock.length != 3) {
			MinecraftForm.sendMessageToLocalChatBox("Auto Quary: DONE!");
			stop();
		}
		Location loc;
		Block next = World.getBlockAt(loc = new Location(startPosition.getX() + nextBlock[1], startPosition.getY() - nextBlock[0], startPosition.getZ() + nextBlock[2]));
		Item.ItemType bestTool = next.getBestTool();
		int tool = (bestTool == Item.ItemType.SHOVEL ? 0 : (bestTool == Item.ItemType.PICKAXE ? 1 : 2));
		Inventory.setActiveItem(tool);
		if(ControlledPlayer.walkTo(loc)[0].equals("true")) {
			ControlledPlayer.faceLocation(loc.clone().shift(0.5, 0, 0.5));
			ControlledPlayer.swingItem(true);
			if(World.getBlockAt(loc).getID() == 0) {
				ControlledPlayer.swingItem(false);
				setBlock(nextBlock, true);
			}
		}
		return 100;
	}
	
	@Override
	public void onPaused() {
		pick = Inventory.contains(ItemType.PICKAXE);
		shovel = Inventory.contains(ItemType.SHOVEL);
	}
	
	public int handleCommand(String command) {
		if(isReady) {
			MinecraftForm.sendMessageToLocalChatBox("Already mining!");
			return 0;
		}
		command = getCommand(command);
		if(command.equalsIgnoreCase("ready")) {
			onPaused();
			if((pick && shovel) || askedReady) {
				isReady = true;
				MinecraftForm.sendMessageToLocalChatBox(((char) 167) + (pick && shovel ? "2Requirments met!" : "4Requirments not met, starting anyway!"));
				return 0;
			} else {
				askedReady = true;
				MinecraftForm.sendMessageToLocalChatBox("Requirments not fully met:");
				MinecraftForm.sendMessageToLocalChatBox("- Pickaxe: " + MinecraftForm.Format.formatTag + (pick ? MinecraftForm.Format.DARK_GREEN.getID() + "found" : MinecraftForm.Format.DARK_RED.getID() + "not found"));
				MinecraftForm.sendMessageToLocalChatBox("- Shovel:  " + MinecraftForm.Format.formatTag + (shovel ? MinecraftForm.Format.DARK_GREEN.getID() + "found" : MinecraftForm.Format.DARK_RED.getID() + "not found"));
				MinecraftForm.sendMessageToLocalChatBox("Enter the ready command to start anyway.");
				return 0;
			}
		} else if(command.split(",").length == 3) {
			String[] parts = command.split(",");
			width = Integer.parseInt(parts[0]);
			length = Integer.parseInt(parts[1]);
			depth = Integer.parseInt(parts[2]);
			return 0;
		} else return 1;
	}
	
	public class Gui extends ScriptGui {
		public int maxWidth = getFontRenderer().getStringWidth("Change this using the command swapping out the variable's for the new variables.");
		
		@Override
		public void render(float partialTick, boolean notControllingPlayer, int mouseX, int mouseY) {
			if(!isReady) {
				drawRect(this.getScaledResolutions()[0] / 2 - (maxWidth + 4) / 2, 2, this.getScaledResolutions()[0] / 2 - (maxWidth + 4) / 2, 60, getColor(0F, 0F, 0F, 0F));
				this.drawCenteredString(getFontRenderer(), "About to quary " + width + " wide, " + length + " long, and " + depth + " deep.", this.getScaledResolutions()[0] / 2, 4, getColor(1F, 1F, 1F, 0F));
				this.drawCenteredString(getFontRenderer(), "Change this using the following command, swapping out the variables.", this.getScaledResolutions()[0] / 2, 14, getColor(1F, 1F, 1F, 0F));
				this.drawCenteredString(getFontRenderer(), "!~tmr:width,length,depth", this.getScaledResolutions()[0] / 2, 24, getColor(1F, 1F, 1F, 0F));
				this.drawCenteredString(getFontRenderer(), "When you're all set, enter the command below to get started!", this.getScaledResolutions()[0] / 2, 44, getColor(1F, 1F, 1F, 0F));
				this.drawCenteredString(getFontRenderer(), "!~tmr:ready", this.getScaledResolutions()[0] / 2, 54, getColor(1F, 1F, 1F, 0F));
			} else {
				if(nextPosition != null) this.drawCenteredString(getFontRenderer(), "Next location: " + nextPosition.toString(), getScaledResolutions()[0] / 2, 11, getColor(255, 255, 255, 0));
			}
		}
	}
}