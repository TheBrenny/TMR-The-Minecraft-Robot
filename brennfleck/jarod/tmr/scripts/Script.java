package brennfleck.jarod.tmr.scripts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import brennfleck.jarod.tmr.TheMinecraftRobot;
import brennfleck.jarod.tmr.scripts.player.Player;
import brennfleck.jarod.tmr.scripts.world.World;
import brennfleck.jarod.tmr.utils.TmrInputProxy;

/**
 * A base class for a script.
 * 
 * @author Jarod Brennfleck
 */
public abstract class Script {
	protected boolean isOkayToUngrabMouse = false;
	private Thread thread;
	private boolean running = false;
	private boolean paused = false;
	private ScriptGui gui;
	
	/**
	 * Do no override, this is what makes the thread a runnable.
	 */
	public Script() {
		thread = new Thread(new Runnable() {
			public void run() {
				try {
					while(running) {
						if(isOkayToUngrabMouse && !TmrInputProxy.isMouseGrabbed()) TmrInputProxy.setMouseGrabbed(false);
						if(!paused && canRunLoop()) thread.sleep(loop());
						else thread.sleep(3000);
						if(Minecraft.getTMR().getScript() != Script.this) running = false;
					}
					running = false;
				} catch(Exception e) {
					e.printStackTrace();
					stop();
				}
			}
		}, "TMR Running Script");
	}
	
	/**
	 * Determines whether or not the script can start. This runs every time the
	 * script is started. Returning <code>true</code> will mean that the script
	 * can successfully start, whereas return <code>false</code> will mean that
	 * the script must abort.
	 */
	public boolean onStart() {
		return true;
	}
	
	/**
	 * The main part of the code that will be looped. The returning long is how
	 * long to suspend script execution for before running the loop again.
	 */
	public abstract long loop();
	
	/**
	 * Called when the script is halted.
	 */
	public void onStop() {}
	
	/**
	 * Called whenever the script is paused or unpaused.
	 */
	public void onPaused() {}
	
	/**
	 * Renders the GUI for the script.
	 */
	public void render(float partialTick, boolean notControllingPlayer, int mouseX, int mouseY) {
		if(gui != null) gui.render(partialTick, notControllingPlayer, mouseX, mouseY);
	}
	
	/**
	 * Starts the script. If a script is already running, then this method
	 * aborts silently.
	 */
	public final void start() {
		if(Minecraft.getTMR().getScript() != null) return;
		System.out.println("Starting script: " + getManifest().name());
		TheMinecraftRobot.sendMessageToLocalChatBox("Starting script: " + getManifest().name());
		running = onStart();
		if(!isValid() || !running) stop();
		if(running) thread.start();
	}
	
	/**
	 * Finishes the current loop and halts script execution until resumed.
	 */
	public final void pause(boolean state) {
		paused = state;
		onPaused();
	}
	
	/**
	 * Halts script execution and removes it as the currently running script.
	 * Once called, there is no more script running.
	 */
	public final void stop() {
		System.out.println("Stopping script: " + getManifest().name());
		TheMinecraftRobot.sendMessageToLocalChatBox("Stopping script: " + getManifest().name());
		onStop();
		Player.resetAttributes();
		running = false;
		paused = false;
		if(Minecraft.getMinecraft().currentScreen == null) TmrInputProxy.setMouseGrabbed(true);
		Minecraft.getTMR().setActiveScript(null);
	}
	
	/**
	 * Returns the current state of the script: <li>0 = Stopped.</li> <li>1 =
	 * Running.</li> <li>2 = Paused.</li>
	 */
	public final int getState() {
		return running ? (paused ? 2 : 1) : 0;
	}
	
	/**
	 * Handles any commands sent. Any non-zero result means a bad command, which
	 * will then be told to the player.
	 */
	public int handleCommand(String command) {
		return 1;
	}
	
	/**
	 * Strips away the prefix of the command. <br>
	 * <br>
	 * EG: <br>
	 * "!~tmr:ready" > "ready"
	 */
	public String getCommand(String command) {
		return command.substring("!~tmr:".length());
	}
	
	/**
	 * Returns the
	 * {@link brennfleck.jarod.tmr.scripts.ScriptManifest#ScriptManifest
	 * ScriptManifest} of the script.
	 */
	public ScriptManifest getManifest() {
		return (ScriptManifest) this.getClass().getAnnotation(ScriptManifest.class);
	}
	
	public abstract class ScriptGui extends Gui {
		public ScriptGui() {
			Script.this.gui = this;
		}
		
		public abstract void render(float partialTick, boolean notControllingPlayer, int mouseX, int mouseY);
	}
	
	/**
	 * Returns whether or not the game loop can run safely.
	 */
	public static final boolean canRunLoop() {
		return isValid() && Minecraft.getMinecraft().currentScreen == null;
	}
	
	/**
	 * Returns whether or not we are valid and ready for script execution.
	 */
	public static final boolean isValid() {
		return Player.isValid() && World.isValid();
	}
}