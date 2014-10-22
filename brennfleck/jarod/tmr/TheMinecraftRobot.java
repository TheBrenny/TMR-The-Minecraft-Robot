package brennfleck.jarod.tmr;

import java.io.File;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.TMRSettings;
import net.minecraft.util.ChatComponentText;
import brennfleck.jarod.tmr.scripts.Script;
import brennfleck.jarod.tmr.scripts.ScriptList;
import brennfleck.jarod.tmr.scripts.entities.ControlledPlayer;
import brennfleck.jarod.tmr.scripts.events.EventObservable;
import brennfleck.jarod.tmr.scripts.events.TMRListener;
import brennfleck.jarod.tmr.scripts.examples.AutoQuary;
import brennfleck.jarod.tmr.scripts.examples.AutoWalker;
import brennfleck.jarod.tmr.scripts.examples.MazeRunner;
import brennfleck.jarod.tmr.scripts.minecraft.MinecraftForm;
import brennfleck.jarod.tmr.utils.TmrInputProxy;

/**
 * Not a good idea to access this from a script... Just sayin'. None of this
 * will have a JavaDoc.
 * 
 * @author Jarod Brennfleck
 */
public class TheMinecraftRobot {
	public static final String TMR_VERSION = "0.5";
	public static final String TMR_TITLE = "The Minecraft Robot";
	private static boolean playerNeedsRemake;
	private Script activeScript;
	public TMRSettings tmrSettings;
	
	public TheMinecraftRobot() {}
	
	public void setActiveScript(Script script) {
		this.activeScript = script;
	}
	
	public Script getScript() {
		return activeScript;
	}
	
	public void handleScriptCommand(String command) {
		if(getScript() != null) {
			if(getScript().handleCommand(command) != 0) MinecraftForm.sendMessageToLocalChatBox(MinecraftForm.Format.DARK_RED.formatCode() + "Bad command!");
		} else {
			MinecraftForm.sendMessageToLocalChatBox("TMR: You can't access a script command without first running a script!");
		}
	}
	
	public void mkdirs(File parent) {
		if(!parent.exists()) parent.mkdir();
		String[] toMake = {"scripts"};
		for(String child : toMake) {
			File query = new File(parent, child);
			if(!query.exists()) query.mkdirs();
		}
	}
	
	public void renderAllGuis(float partialTick, boolean notControllingPlayer, int mouseX, int mouseY) {
		if(getScript() != null) getScript().render(partialTick, notControllingPlayer, mouseX, mouseY);
	}
	
	public void initPlayer() {
		playerNeedsRemake = true;
		new ControlledPlayer();
	}
	
	public void initExtras() {
		try {
			TmrInputProxy.initRobot();
			tmrSettings = new TMRSettings(Minecraft.getMinecraft().tmrDataDir);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addDefaultListeners(EventObservable eo) {
		// Add stuff here later...
	}
	
	public static boolean playerNeedsRemake() {
		boolean tmp = playerNeedsRemake;
		playerNeedsRemake = false;
		return tmp;
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