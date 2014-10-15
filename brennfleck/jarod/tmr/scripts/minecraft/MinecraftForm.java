package brennfleck.jarod.tmr.scripts.minecraft;

import org.lwjgl.input.Mouse;

import brennfleck.jarod.tmr.TheMinecraftRobot;
import brennfleck.jarod.tmr.scripts.ScriptManifest;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class MinecraftForm {
	private static Minecraft getMinecraft() {
		return Minecraft.getMinecraft();
	}
	
	public static void grabMouse() {
		if(getMinecraft().currentScreen == null) getMinecraft().mouseHelper.grabMouseCursor();
	}
	
	public static void ungrabMouse() {
		getMinecraft().mouseHelper.ungrabMouseCursor();
	}
	
	public static boolean isMouseGrabbed() {
		return Mouse.isGrabbed();
	}
	
	public static void sendMessageToLocalChatBox(String message) {
		if(Minecraft.getTMR().getScript() != null) {
			String prepend = Minecraft.getTMR().getScript().getManifest().name() + ": ";
			if(!message.contains(prepend) && !message.contains("TMR: ")) message = prepend + message;
		} else if(!message.contains("TMR: ")) message = "TMR: " + message;
		TheMinecraftRobot.sendMessageToLocalChatBox(message);
	}
	
	public enum Color {
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
		WHITE("f");
		private String id;
		
		Color(String id) {
			this.id = id;
		}
		
		public String colorCode() {
			return ((char) 167) + id;
		}
		
		public String getID() {
			return id;
		}
		
		public static String codeTag() {
			return ((char) 167) + "";
		}
	}
	
	public enum Format {
		OBFUSCATED("k"),
		BOLD("l"),
		STRIKE_THROUGH("m"),
		UNDERLINE("n"),
		ITALIC("o"),
		RESET("r"),
		EXTRA_LINE("\n");
		private String code;
		
		Format(String code) {
			this.code = code;
		}
		
		public String formatCode() {
			return ((char) 167) + code;
		}
		
		public String getCode() {
			return code;
		}
		
		public static String codeTag() {
			return ((char) 167) + "";
		}
	}
}