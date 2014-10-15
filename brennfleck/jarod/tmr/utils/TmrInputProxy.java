package brennfleck.jarod.tmr.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import brennfleck.jarod.helpfulthings.BrennyHelpful;
import brennfleck.jarod.tmr.scripts.minecraft.MinecraftForm;

public class TmrInputProxy {
	private static Robot robot;
	private static Method currentMethod;
	public static final int MOUSE_BUTTON1 = InputEvent.BUTTON1_DOWN_MASK;
	public static final int MOUSE_BUTTON2 = InputEvent.BUTTON2_DOWN_MASK;
	public static final int MOUSE_BUTTON3 = InputEvent.BUTTON3_DOWN_MASK;
	
	public static Method getCurrentMethod() {
		return currentMethod;
	}
	
	public static void setCurrentMethod(Method method) {
		currentMethod = method;
	}
	
	public static void setMouseGrabbed(boolean state) {
		if(state) grabMouse();
		else ungrabMouse();
	}
	
	public static void grabMouse() {
		MinecraftForm.grabMouse();
	}
	
	public static void ungrabMouse() {
		MinecraftForm.ungrabMouse();
	}
	
	public static boolean isMouseGrabbed() {
		return MinecraftForm.isMouseGrabbed();
	}
	
	public static void moveMouseTo(int posX, int posY) {
		switch(currentMethod) {
		default:
		case LWJGL:
			Mouse.setCursorPosition(posX, posY);
			break;
		case ROBOT:
			robot.mouseMove(posX, posY);
			break;
		}
	}
	
	public static void mouseClick(int buttons) {
		switch(currentMethod) {
		default:
		case LWJGL:
		case ROBOT:
			mousePress(buttons);
			mouseRelease(buttons);
			break;
		}
	}
	
	public static void mousePress(int buttons) {
		switch(currentMethod) {
		default:
		case LWJGL:
			System.out.println("No mouse press method for LWJGL...");
		case ROBOT:
			robot.mousePress(buttons);
			break;
		}
	}
	
	public static void mouseRelease(int buttons) {
		switch(currentMethod) {
		default:
		case LWJGL:
			System.out.println("No mouse release method for LWJGL...");
		case ROBOT:
			robot.mouseRelease(buttons);
			break;
		}
	}
	
	public static int getKeyCodeFromChar(char ch) {
		return KeyEvent.getExtendedKeyCodeForChar(ch);
	}
	
	public static int getKeyCodeFromChar(String ch) {
		if(BrennyHelpful.MathHelpful.stringNullOrLengthZero(ch)) return -1;
		return KeyEvent.getExtendedKeyCodeForChar((int) ((char) ch.charAt(0)));
	}
	
	public static void keyPress(int keyCode) {
		switch(currentMethod) {
		default:
		case LWJGL:
			System.out.println("No key press method for LWJGL...");
		case ROBOT:
			robot.keyPress(keyCode);
			break;
		}
	}
	
	public static void keyRelease(int keyCode) {
		switch(currentMethod) {
		default:
		case LWJGL:
			System.out.println("No key release method for LWJGL...");
		case ROBOT:
			robot.keyRelease(keyCode);
			break;
		}
	}
	
	public static void keyType(int keyCode) {
		switch(currentMethod) {
		default:
		case LWJGL:
		case ROBOT:
			keyPress(keyCode);
			keyRelease(keyCode);
			break;
		}
	}
	
	public static void keysType(int[] keyCodes) {
		for(int keyCode : keyCodes) {
			keyPress(keyCode);
			keyRelease(keyCode);
		}
	}
	
	public static void typeString(String str) {
		for(char c : str.toCharArray()) {
			keyType(getKeyCodeFromChar(c));
		}
	}
	
	public enum Method {
		ROBOT,
		LWJGL;
	}
	
	public static void initRobot() throws AWTException {
		robot = new Robot();
		robot.setAutoDelay(100);
	}
}