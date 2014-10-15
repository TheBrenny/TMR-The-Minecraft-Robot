package brennfleck.jarod.tmr.scripts.examples;

import brennfleck.jarod.tmr.scripts.Script;
import brennfleck.jarod.tmr.scripts.ScriptManifest;
import brennfleck.jarod.tmr.scripts.Script.ScriptGui;
import brennfleck.jarod.tmr.scripts.player.Player;
import brennfleck.jarod.tmr.scripts.world.World;
import net.minecraft.client.Minecraft;

@ScriptManifest(name = "Auto Walker", author = "Jarod Brennfleck", version = "0.3", category = "Walking")
public class AutoWalker extends Script {
	public boolean onStart() {
		new Gui();
		Player.move(Player.FORWARD, true);
		return true;
	}
	public long loop() {
		Player.jump();
		return 1000;
	}
	public class Gui extends ScriptGui {
		public void render(float partialTick, boolean notControllingPlayer, int mouseX, int mouseY) {
			this.drawCenteredString(this.getFontRenderer(), "Auto walking forward...", this.getScaledResolutions()[0] / 2, 6, this.getColor(255, 255, 255, 0));
		}
	}
}