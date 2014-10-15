package brennfleck.jarod.tmr.scripts;

import brennfleck.jarod.tmr.TheMinecraftRobot;
import brennfleck.jarod.tmr.scripts.minecraft.MinecraftForm;
import brennfleck.jarod.tmr.scripts.player.Player;
import brennfleck.jarod.tmr.scripts.world.World;
import brennfleck.jarod.tmr.utils.TmrInputProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public abstract class Script {
	protected boolean isOkayToUngrabMouse = false;
	private Thread thread;
	private boolean running = false;
	private boolean paused = false;
	private ScriptGui gui;
	
	public Script() {
		thread = new Thread(new Runnable() {
			public void run() {
				try {
					while(running) {
						if(isOkayToUngrabMouse && !TmrInputProxy.isMouseGrabbed()) TmrInputProxy.ungrabMouse();
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
	
	public boolean onStart() {
		return true;
	}
	
	public abstract long loop();
	
	public void onStop() {}
	
	public void onPaused() {}
	
	public final void render(float partialTick, boolean notControllingPlayer, int mouseX, int mouseY) {
		if(gui != null) gui.render(partialTick, notControllingPlayer, mouseX, mouseY);
	}
	
	public abstract class ScriptGui extends Gui {
		public ScriptGui() {
			Script.this.gui = this;
		}
		
		public abstract void render(float partialTick, boolean notControllingPlayer, int mouseX, int mouseY);
	}
	
	public final void start() {
		System.out.println("Starting script: " + getManifest().name());
		TheMinecraftRobot.sendMessageToLocalChatBox("Starting script: " + getManifest().name());
		running = onStart();
		if(!isValid() || !running) stop();
		if(running) thread.start();
	}
	
	public final void pause(boolean state) {
		paused = state;
		onPaused();
	}
	
	public final void stop() {
		System.out.println("Stopping script: " + getManifest().name());
		TheMinecraftRobot.sendMessageToLocalChatBox("Stopping script: " + getManifest().name());
		onStop();
		Player.resetAttributes();
		running = false;
		paused = false;
		TmrInputProxy.grabMouse();
		Minecraft.getTMR().setActiveScript(null);
	}
	
	public final int getState() {
		return running ? (paused ? 2 : 1) : 0;
	}
	
	public int handleCommand(String command) {return 1;}
	public String getCommand(String command) {
		return command.substring("!~tmr:".length());
	}
	public ScriptManifest getManifest() {
		return (ScriptManifest) this.getClass().getAnnotation(ScriptManifest.class);
	}
	
	public static final boolean canRunLoop() {
		return isValid() && Minecraft.getMinecraft().currentScreen == null;
	}
	
	public static final boolean isValid() {
		return Player.isValid() && World.isValid();
	}
}