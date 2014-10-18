package brennfleck.jarod.tmr.scripts.minecraft;

import org.lwjgl.input.Mouse;

import brennfleck.jarod.tmr.TheMinecraftRobot;
import brennfleck.jarod.tmr.scripts.ScriptManifest;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

/**
 * This class controls everything that has anything to do with the Minecraft
 * window or Minecraft itself.
 * 
 * @author Jarod Brennfleck
 */
public class MinecraftForm {
	private static Minecraft getMinecraft() {
		return Minecraft.getMinecraft();
	}
	
	/**
	 * Grabs the mouse and hides it.
	 */
	public static void grabMouse() {
		if(getMinecraft().currentScreen == null) getMinecraft().mouseHelper.grabMouseCursor();
	}
	
	/**
	 * Ungrabs the mouse and shows it.
	 */
	public static void ungrabMouse() {
		getMinecraft().mouseHelper.ungrabMouseCursor();
	}
	
	/**
	 * Checks whether or not the mouse has been grabbed.
	 */
	public static boolean isMouseGrabbed() {
		return Mouse.isGrabbed();
	}
	
	/**
	 * Sends a message to the chat box, but only for the local user; nothing is
	 * sent to the server.
	 */
	public static void sendMessageToLocalChatBox(String message) {
		if(Minecraft.getTMR().getScript() != null) {
			String prepend = Minecraft.getTMR().getScript().getManifest().name() + ": ";
			if(!message.contains(prepend) && !message.contains("TMR: ")) message = prepend + message;
		} else if(!message.contains("TMR: ")) message = "TMR: " + message;
		TheMinecraftRobot.sendMessageToLocalChatBox(message);
	}
	
	/**
	 * Holds data for writing colorful things to the chatbox.
	 * 
	 * @author Jarod Brennfleck
	 */
	public enum Format {
		BLACK("0"),
		DARK_BLUE("1"),
		DARK_GREEN("2"),
		DARK_AQUA("3"),
		DARK_RED("4"),
		DARK_PURPLE("5"),
		GOLD("6"),
		GRAY("7"),
		DARK_GRAY("8"),
		BLUE("9"),
		GREEN("a"),
		AQUA("b"),
		RED("c"),
		LIGHT_PURPLE("d"),
		YELLOW("e"),
		WHITE("f"),
		OBFUSCATED("k"),
		BOLD("l"),
		STRIKE_THROUGH("m"),
		UNDERLINE("n"),
		ITALIC("o"),
		RESET("r"),
		EXTRA_LINE("\n");
		/**
		 * Returns the tag used to process text manipulation.
		 */
		public static String formatTag = ((char) 167) + "";
		private String id;
		
		Format(String id) {
			this.id = id;
		}
		
		/**
		 * Returns the full format code, including the {@link #formatTag()
		 * format tag}.
		 */
		public String formatCode() {
			return ((char) 167) + id;
		}
		
		/**
		 * Returns the format ID used in the format code.
		 */
		public String getID() {
			return id;
		}
	}
}