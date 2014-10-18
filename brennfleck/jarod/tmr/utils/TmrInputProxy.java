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

/**
 * A proxy for input into the game. Used for reflective work, rather than
 * injective.
 * 
 * @author Jarod Brennfleck
 */
public class TmrInputProxy {
	private static Robot robot;
	private static Method currentMethod;
	public static final int MOUSE_BUTTON1 = InputEvent.BUTTON1_DOWN_MASK;
	public static final int MOUSE_BUTTON2 = InputEvent.BUTTON2_DOWN_MASK;
	public static final int MOUSE_BUTTON3 = InputEvent.BUTTON3_DOWN_MASK;
	
	/**
	 * Returns the current input method.
	 */
	public static Method getCurrentMethod() {
		return currentMethod;
	}
	
	/**
	 * Sets the current input {@link TmrInputProxy.Method#Method()}.
	 */
	public static void setCurrentMethod(Method method) {
		currentMethod = method;
	}
	
	/**
	 * Sets whether or not to grab the mouse cursor.
	 */
	public static void setMouseGrabbed(boolean state) {
		if(state) grabMouse();
		else ungrabMouse();
	}
	
	/**
	 * DEPRECATED - Instead, use: {@link #setMouseGrabbed(boolean)} <br>
	 * <br>
	 * Grabs the mouse as to hide the cursor and lock it to the current screen.
	 */
	@Deprecated
	public static void grabMouse() {
		MinecraftForm.grabMouse();
	}
	
	/**
	 * DEPRECATED - Instead, use: {@link #setMouseGrabbed(boolean)} <br>
	 * <br>
	 * Releases the mouse as to show the cursor and unlock it from the current
	 * screen.
	 */
	@Deprecated
	public static void ungrabMouse() {
		MinecraftForm.ungrabMouse();
	}
	
	/**
	 * Returns whether or not the mouse has been grabbed.
	 */
	public static boolean isMouseGrabbed() {
		return MinecraftForm.isMouseGrabbed();
	}
	
	/**
	 * Moves the mouse to the coordinates using the currently set input method.
	 */
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
	
	/**
	 * Clicks the mouse using the {@link java.awt.Robot#Robot() robot} since
	 * LWJGL does not contain any mouse click functions.
	 * 
	 * @see #mousePress(int)
	 * @see #mouseRelease(int)
	 */
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
	
	/**
	 * Presses the mouse using the {@link java.awt.Robot#Robot() robot} since
	 * LWJGL does not contain any mouse press functions.
	 * 
	 * @see #mouseClick(int)
	 * @see #mouseRelease(int)
	 */
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
	
	/**
	 * Releases the mouse using the {@link java.awt.Robot#Robot() robot} since
	 * LWJGL does not contain any mouse release functions.
	 * 
	 * @see #mouseClick(int)
	 * @see #mousePress(int)
	 */
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
	
	/**
	 * Returns the keycode for the character.
	 */
	public static int getKeyCodeFromChar(char ch) {
		return KeyEvent.getExtendedKeyCodeForChar(ch);
	}
	
	/**
	 * Returns the keycode for the first index of the string.
	 */
	public static int getKeyCodeFromChar(String ch) {
		if(BrennyHelpful.MathHelpful.stringNullOrLengthZero(ch)) return -1;
		return KeyEvent.getExtendedKeyCodeForChar((int) ((char) ch.charAt(0)));
	}
	
	/**
	 * Presses a key using the {@link java.awt.Robot#Robot() robot} since LWJGL
	 * does not contain any key press functions.
	 * 
	 * @see #keyPress(int)
	 * @see #keyType(int)
	 * @see #keysType(int[])
	 * @see #typeString(String)
	 */
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
	
	/**
	 * Releases a key using the {@link java.awt.Robot#Robot() robot} since LWJGL
	 * does not contain any key release functions.
	 * 
	 * @see #keyPress(int)
	 * @see #keyType(int)
	 * @see #keysType(int[])
	 * @see #typeString(String)
	 */
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
	
	/**
	 * Types a key using the {@link java.awt.Robot#Robot() robot} since LWJGL
	 * does not contain any key type functions.
	 * 
	 * @see #keyPress(int)
	 * @see #keyRelease(int)
	 * @see #keysType(int[])
	 * @see #typeString(String)
	 */
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
	
	/**
	 * Types an array of keys using the {@link java.awt.Robot#Robot() robot}
	 * since LWJGL does not contain any key type functions.
	 * 
	 * @see #keyPress(int)
	 * @see #keyRelease(int)
	 * @see #keyType(int)
	 * @see #typeString(String)
	 */
	public static void keysType(int[] keyCodes) {
		for(int keyCode : keyCodes) {
			keyPress(keyCode);
			keyRelease(keyCode);
		}
	}
	
	/**
	 * Types string using the {@link java.awt.Robot#Robot() robot} since LWJGL
	 * does not contain any key type functions.
	 * 
	 * @see #keyPress(int)
	 * @see #keyRelease(int)
	 * @see #keyType(int)
	 * @see #keysType(int[])
	 */
	public static void typeString(String str) {
		for(char c : str.toCharArray()) {
			keyType(getKeyCodeFromChar(c));
		}
	}
	
	/**
	 * An enum to hold which method to use.
	 * 
	 * @author Jarod Brennfleck
	 */
	public enum Method {
		ROBOT,
		LWJGL;
	}
	
	/**
	 * Initialises any input methods that need to be made: <li>
	 * {@link java.awt.Robot#Robot() Robot}. <br>
	 * <br>
	 * This should never be used unless the robot bugs up, but even then,
	 * scripts should not access this.
	 * 
	 * @throws AWTException
	 */
	public static void initRobot() throws AWTException {
		robot = new Robot();
		robot.setAutoDelay(100);
	}
}