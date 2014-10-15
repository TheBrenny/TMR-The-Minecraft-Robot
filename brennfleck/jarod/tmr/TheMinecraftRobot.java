package brennfleck.jarod.tmr;

import java.awt.Robot;
import java.io.File;
import java.io.IOException;

import brennfleck.jarod.tmr.scripts.Script;
import brennfleck.jarod.tmr.scripts.ScriptList;
import brennfleck.jarod.tmr.scripts.ScriptManifest;
import brennfleck.jarod.tmr.scripts.examples.AutoQuary;
import brennfleck.jarod.tmr.scripts.examples.AutoWalker;
import brennfleck.jarod.tmr.scripts.examples.MazeRunner;
import brennfleck.jarod.tmr.scripts.minecraft.MinecraftForm;
import brennfleck.jarod.tmr.utils.TmrInputProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class TheMinecraftRobot {
	public static final String TMR_VERSION = "0.3";
	public static final String TMR_TITLE = "The Minecraft Robot";
	private Script activeScript;
	
	public TheMinecraftRobot() {
		initExtras();
	}
	
	public void setActiveScript(Script script) {
		this.activeScript = script;
	}
	
	public Script getScript() {
		return activeScript;
	}
	
	public void handleScriptCommand(String command) {
		if(getScript() != null) {
			if(getScript().handleCommand(command) != 0) MinecraftForm.sendMessageToLocalChatBox(MinecraftForm.Color.DARK_RED.colorCode() + "Bad command!");
		} else {
			MinecraftForm.sendMessageToLocalChatBox("TMR: You can't access a script command without first running a script!");
		}
	}
	
	public void mkdirs(File parent) {
		try {
			if(!parent.exists()) parent.mkdir();
			String[] toMake = {"scripts", "settings.txt"};
			for(String child : toMake) {
				File query = new File(parent, child);
				if(!query.exists()) {
					if(child.contains(".")) query.createNewFile();
					else query.mkdirs();
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void renderAllGuis(float partialTick, boolean notControllingPlayer, int mouseX, int mouseY) {
		if(getScript() != null) getScript().render(partialTick, notControllingPlayer, mouseX, mouseY);
	}
	
	public void initExtras() {
		try {
			TmrInputProxy.initRobot();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void collectScripts() {
		ScriptList.reset();
		ScriptList.addScript(AutoWalker.class);
		ScriptList.addScript(AutoQuary.class);
		ScriptList.addScript(MazeRunner.class);
		ScriptList.getAllScripts();
	}
	
	public static void sendMessageToLocalChatBox(String message) {
		Minecraft.getMinecraft().ingameGUI.getChatGUI().chatMessage(new ChatComponentText(message));
	}
}