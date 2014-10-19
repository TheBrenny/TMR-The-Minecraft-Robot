package brennfleck.jarod.tmr.scripts.examples;

import java.text.DecimalFormat;
import java.util.ArrayList;

import brennfleck.jarod.helpfulthings.BrennyHelpful;
import brennfleck.jarod.pathing.AStar;
import brennfleck.jarod.tmr.scripts.Script;
import brennfleck.jarod.tmr.scripts.ScriptManifest;
import brennfleck.jarod.tmr.scripts.ScriptManifest.Category;
import brennfleck.jarod.tmr.scripts.entities.TemporaryPlayerNameTakeover;
import brennfleck.jarod.tmr.scripts.minecraft.MinecraftForm;
import brennfleck.jarod.tmr.scripts.world.Location;


@ScriptManifest(name = "Maze Runner", version = "0.1", author = "Jarod Brennfleck", category = Category.WALKING)
public class MazeRunner extends Script {
	public ArrayList<Location> currentPath;
	public int maxSteps;
	public int stepsComplete;
	public Location target;
	public Location nextLocation;
	public Location currentLocation;
	
	public boolean onStart() {
		new Gui();
		return true;
	}
	
	public long loop() {
		if(target == null) return 3000;
		String[] a = TemporaryPlayerNameTakeover.walkTo(target);
		nextLocation = new Location(Double.parseDouble(a[1]), Double.parseDouble(a[2]), Double.parseDouble(a[3]));
		currentLocation = TemporaryPlayerNameTakeover.getLocation("").shift(0, -1, 0).getNonPrecise();
		if(a[0].equals("true")) {
			MinecraftForm.sendMessageToLocalChatBox("Completed run!");
			stop();
		}
		if(nextLocation.equals(currentLocation)) stepsComplete++;
		return 100;
	}
	
	public void createPath(int x, int y, int z) {
		Location l;
		AStar path = TemporaryPlayerNameTakeover.makePath(l = new Location(x, y, z));
		if(path != null) {
			currentPath = TemporaryPlayerNameTakeover.getCurrentPath();
			this.maxSteps = currentPath.size();
			stepsComplete = 0;
			target = l;
		} else {
			stop();
		}
	}
	
	public int handleCommand(String command) {
		if(target == null) {
			String[] cmd = getCommand(command).split(",");
			if(cmd.length == 3) {
				try {
					int x = Integer.valueOf(cmd[0]);
					int y = Integer.valueOf(cmd[1]);
					int z = Integer.valueOf(cmd[2]);
					createPath(x, y, z);
				} catch(Exception e) {
					e.printStackTrace();
					return 1;
				}
				return 0;
			} else return 1;
		} else {
			MinecraftForm.sendMessageToLocalChatBox("Already running!");
			return 0;
		}
	}
	
	public class Gui extends ScriptGui {
		public void render(float partialTick, boolean notControllingPlayer, int mouseX, int mouseY) {
			int[] sr = getScaledResolutions();
			if(target == null) {
				String[] strs = new String[] {"Type in:", "!~tmr:x,y,z", "To walk to the coordinates <x>, <y>, <z>."};
				for(int i = 0; i < strs.length; i++) drawCenteredString(getFontRenderer(), strs[i], sr[0] / 2, 12 * (i + 1), getColor(1.0F, 1.0F, 1.0F, 0.0F));
			} else {
				String progress = "<";
				int i = 0;
				int barsToShow = (int) ((double) (BrennyHelpful.getPercentage(BrennyHelpful.getPercentage(stepsComplete, maxSteps), 10) / 10));
				for(; i < barsToShow; i++) progress += "=";
				int halfWidth = 12 * getFontRenderer().getCharWidth('=') / 2;
				String percentage = String.format("[%.2f%%]", BrennyHelpful.getPercentage(stepsComplete, maxSteps));
				drawString(getFontRenderer(), progress, sr[0] / 2 - halfWidth, 12, getColor(1.0F, 1.0F, 1.0F, 0.0F));
				drawString(getFontRenderer(), ">", sr[0] / 2 + halfWidth, 12, getColor(1.0F, 1.0F, 1.0F, 0.0F));
				drawCenteredString(getFontRenderer(), percentage, sr[0] / 2, 24, getColor(1.0F, 1.0F, 1.0F, 0.0F));
			}
		}
	}
}